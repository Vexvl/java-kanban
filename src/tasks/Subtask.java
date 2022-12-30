package tasks;

public class Subtask extends Task {

    public String status;

    public Subtask(String name, String description, int idnumber, String status) {
        super(name, description, idnumber, status);
        this.name = name;
        this.description = description;
        this.idnumber = idnumber;
        this.status = status;
    }
    @Override
    public String toString(){
        return name + ", " + description + ", " + status;
    }
}
