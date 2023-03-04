package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {

    private final int epicId;

    private boolean isSubtask = true;
    private int duration;

    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private Type type = Type.SUBTASK;
    public Subtask(String name, String description, int id, Status status, int epicId, int duration, String startTime) {
        super(name, description, id, status, duration, startTime);
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.epicId = epicId;
        this.duration = duration;
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
        return id + "," + type + "," + name + "," + status + "," + description + "," + epicId + "," + startTime.toString() + "," + duration + "," +  getEndTime().toString();
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

        return Objects.equals(name, subtask.name) && Objects.equals(description, subtask.description) && Objects.equals(id, subtask.id) && Objects.equals(type, subtask.type) && Objects.equals(status, subtask.status) && Objects.equals(duration, subtask.duration)&& Objects.equals(startTime, subtask.startTime) && Objects.equals(endTime, subtask.endTime) && Objects.equals(epicId, subtask.epicId);

    }
}