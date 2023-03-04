package model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    private List<Subtask> subtasks;

    private Type type = Type.EPIC;

    private int duration;

    private LocalDateTime endTime;

    private LocalDateTime startTime;

    public Epic(String name, String description, int id, Status status) {
        super(name, description, id, status);
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        subtasks = new ArrayList<>();
    }

    public void calculateDuration() {
        if (!subtasks.isEmpty()) {
            int sumOfDurationSubtasks = 0;
            LocalDateTime minLocalDateTime = LocalDateTime.of(1, Month.JANUARY, 1, 1, 1, 1);
            LocalDateTime maxLocalDateTime = LocalDateTime.of(10000, Month.JANUARY, 1, 1, 1, 1);
            for (Subtask subtask : subtasks) {
                sumOfDurationSubtasks += subtask.getDuration();
                if (subtask.getLocalDateTime().isAfter(minLocalDateTime)) {
                    minLocalDateTime = subtask.getLocalDateTime();
                }
                if (subtask.getEndTime().plusMinutes(subtask.getDuration()).isBefore(maxLocalDateTime)) {
                    maxLocalDateTime = subtask.getEndTime().plusMinutes(subtask.getDuration());
                }
            }
            startTime = minLocalDateTime;
            duration = sumOfDurationSubtasks;
            endTime = maxLocalDateTime.plusMinutes(sumOfDurationSubtasks);
        }
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setEpicStatus(Status status) {
        this.status = status;
    }

    public Status getEpicStatus() {
        return status;
    }

    public int getEpicId() {
        return id;
    }

    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description + "," + startTime + "," + duration + "," + endTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Epic epic = (Epic) obj;

        return Objects.equals(name, epic.name) && Objects.equals(description, epic.description) && Objects.equals(id, epic.id) && Objects.equals(type, epic.type) && Objects.equals(status, epic.status) && Objects.equals(duration, epic.duration) && Objects.equals(startTime, epic.startTime) && Objects.equals(subtasks, epic.subtasks) && Objects.equals(endTime, epic.endTime);

    }

}