package model;

public class Subtask extends Task {

    private int epicId;

    private boolean ifSubtask = true;

    public Subtask(String name, String description, int id, Status status, int epicId) {
        super(name, description, id, status);
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.epicId = epicId;
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

    public Integer getEpicId() {
        return epicId;
    }
    public Boolean getIfSubTask() {
        return ifSubtask;
    }
}