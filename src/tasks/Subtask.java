package tasks;

public class Subtask extends Task {

    private int epicId;

    public Subtask(String name, String description, int id, String status, int epicId) {
        super(name, description, id, status);
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.epicId = epicId;
    }
    @Override
    public String toString(){
        return name + ", " + description + ", " + status;
    }
    public String getStatusSubtask(){
        return status;
    }
    public void setStatusSubtask(String status){
        this.status = status;
    }
}