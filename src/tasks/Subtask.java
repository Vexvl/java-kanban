package tasks;

import model.Status;

public class Subtask extends Task {

    private final int epicId;

    public Subtask(String name, String description, int id, Status status, int epicId) {
        super(name, description, id, status);
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return name + ", " + description + ", " + status;
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
}