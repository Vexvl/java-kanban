package model;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Subtask> subtasks;

    private boolean ifEpic;

    public Epic(String name, String description, int id, Status status, boolean ifEpic) {
        super(name, description, id, status, ifEpic);
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.ifEpic = ifEpic;
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

    public Boolean getIfEpic(){
        return ifEpic;
    }
}