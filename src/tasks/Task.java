package tasks;

public class Task {

    protected int id;
    protected String name;
    protected String description;
    protected String status;

    public Task(String name, String description, int id, String status){
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }
    @Override
    public String toString(){
        return name + ", " + description + ", " + status;
    }
}