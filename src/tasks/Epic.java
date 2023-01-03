package tasks;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Subtask> subtasks;

    public Epic(String name, String description, int id, String status) {
        super(name, description, id, status);
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        subtasks = new ArrayList<>();
    }
    @Override
    public String toString(){
        return name + ", " + description + ", " + status;
    }
    public ArrayList getArraySubtasks(){
        return subtasks;
    }
    public void setEpicStatus(String status){
        this.status = status;
    }

    public ArrayList<Subtask> getArrayOfSubtasksByEpic(){
        return subtasks;
    }
}