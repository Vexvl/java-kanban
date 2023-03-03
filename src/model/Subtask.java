package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Subtask extends Task {

    private final int epicId;

    private boolean isSubtask = true;
    private int minutesToDo;

    private LocalDateTime endTime;
    private Duration duration;
    private LocalDateTime startTime;
    private Type type = Type.SUBTASK;
    public Subtask(String name, String description, int id, Status status, int epicId, int minutesToDo, String startTime) {
        super(name, description, id, status, minutesToDo, startTime);
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.epicId = epicId;
        this.minutesToDo = minutesToDo;
        this.duration = duration.ofMinutes(minutesToDo);
        this.startTime = LocalDateTime.parse(startTime);
        this.endTime = setEndTime();
    }

    public Status getStatusSubtask() {
        return status;
    }

    public void setStatusSubtask(Status status) {
        this.status = status;
    }

    public Integer getIdOfSubtask() {
        return id;
    }

    public Integer getEpicIdOfSubtask() {
        return epicId;
    }

    public int getEpicId() {
        return epicId;
    }
    public String getDescription(){
        return description;
    }
    public String getName(){
        return name;
    }
    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description + "," + epicId + + duration.toMinutes() + "," + startTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + getEndTime().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Subtask subtask = (Subtask) obj;

        return Objects.equals(name, subtask.name) && Objects.equals(description, subtask.description) && Objects.equals(id, subtask.id) && Objects.equals(type, subtask.type) && Objects.equals(status, subtask.status) && Objects.equals(minutesToDo, subtask.minutesToDo)&& Objects.equals(startTime, subtask.startTime) && Objects.equals(endTime, subtask.endTime) && Objects.equals(epicId, subtask.epicId);

    }
}