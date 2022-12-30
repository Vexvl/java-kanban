package tasks;
import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<Subtask> subtasks;
    public String status;

    public Epic(String name, String description, int idnumber, String status) {
        super(name, description, idnumber, status);
        this.name = name;
        this.description = description;
        this.idnumber = idnumber;
        this.status = status;
        subtasks = new ArrayList<>();
    }
    @Override
    public String toString(){
        return name + ", " + description + ", " + status;
    }
}
