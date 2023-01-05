package tasks;

import status.Status;

public class Task {

    protected int id;
    protected String name;
    protected String description;
    protected Status status;

    public Task(String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return name + ", " + description + ", " + status;
    }

    public Integer getIdOfTask() {
        return id;
    }
}