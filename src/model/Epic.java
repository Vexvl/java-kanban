package model;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Subtask> subtasks;

    private int epicId;

    private boolean isSubtask = false;


    public Epic(String name, String description, int id, Status status, int epicId) {
        super(name, description, id, status);
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.epicId = epicId;
        subtasks = new ArrayList<>();
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

    public Integer getEpicId() {
        return epicId;
    }
    public boolean isSubtask(){
        return isSubtask;
    }

}