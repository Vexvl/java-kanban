package tasks;

import status.Status;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Subtask> subtasks;

    public Epic(String name, String description, int id, Status status) {
        super(name, description, id, status);
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        subtasks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name + ", " + description + ", " + status;
    }

    public ArrayList getArraySubtasks() {
        return subtasks;
    }

    public void setEpicStatus(Status status) {
        this.status = status;
    }

    public ArrayList<Subtask> getArrayOfSubtasksByEpic() {
        return subtasks;
    }

    public Integer getIdOfEpic() {
        return id;
    }
}