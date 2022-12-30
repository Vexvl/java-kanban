package tasks;

public class Task {
    protected String name;
    protected String description;
    public int idnumber;
    protected String status;
    public Task(String name, String description, int idnumber, String status){
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
