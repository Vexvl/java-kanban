package manager;

import java.util.ArrayList;
import java.util.HashMap;
import tasks.Epic;
import tasks.Task;
import tasks.Subtask;

public class Manager {

    protected int id = 1;

    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subTasks = new HashMap<>();


    public void createTask(String name, String description) {
        Task task = new Task(name, description, id, "NEW");
        tasks.put(id, task);
        id++;
    }

    public void createEpic(String name, String description) {
        Epic epic = new Epic(name, description, id, "NEW");
        epics.put(id, epic);
        id++;
    }

    public void createSubTask(String name, String description, int epicId) {
        Subtask subtask = new Subtask(name, description, id, "NEW", epicId);
        subTasks.put(id, subtask);
        epics.get(epicId).getArraySubtasks().add(subtask);
        id++;
    }

    public HashMap getAllTasks() {
        return tasks;
    }

    public HashMap getAllEpics() {
        return epics;
    }

    public HashMap getAllSubTasks() {
        return subTasks;
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteSubTaskById(int id) {
        subTasks.remove(id);
    }

    public void deleteEpicById(int id) {
        epics.remove(id);
    }

    public void deleteAll() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Subtask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public ArrayList getAllSubTasksByEpic(Epic epic){
        return epic.getArraySubtasks();
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(tasks.get(id))) {
            tasks.put(id, task);
        }
    }

    public void updateEpic(Epic epic) {
        if (tasks.containsKey(epics.get(id))) {
            tasks.put(id, epic);
        }
    }

    public void updateSubTask(Subtask subtask) {
        if (tasks.containsKey(subTasks.get(id))) {
            tasks.put(id, subtask);
        }
    }
    public void updateStatusOfEpic(Epic epic){
        if (epic.getArraySubtasks().isEmpty() == true){
            epic.setEpicStatus("NEW");
        }
        else if (!epic.getArraySubtasks().isEmpty()){
            int doneTask = 0;
            int toDoTask = subTasks.size();
            for (Subtask subtask : epic.subtasks){
                if (subtask.getStatusSubtask().equals("DONE")){
                    doneTask++;
                }
            }
            if(doneTask==toDoTask){
                epic.setEpicStatus("DONE");
            }
            else epic.setEpicStatus("IN PROGRESS");
        }
    }
    public void changeStatusSubtasktoDone(int id){
        subTasks.get(id).setStatusSubtask("DONE");
    }
    public HashMap getEpics(){
        return epics;
    }
    public HashMap getSubtasks(){
        return subTasks;
    }
    public HashMap getTasks(){
        return tasks;
    }
}