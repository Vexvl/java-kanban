package model;

public class Task {

    protected int id;
    protected String name;
    protected String description;
    protected Status status;
    private Type type = Type.TASK;

    private Boolean isSubtask = false;

    private int epicId = 0;

    public Task(String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description;
    }

    public Integer getIdOfTask() {
        return id;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public Type getType() {
        return type;
    }
}