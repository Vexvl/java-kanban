package service;

import java.util.ArrayList;
import java.util.HashMap;

import model.Epic;
import model.Task;
import model.Subtask;
import model.Status;

public class InMemoryTaskManager implements TaskManager {

    private int id = 1;

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subTasks = new HashMap<>();

    @Override
    public Task createTask(String name, String description) {
        Task task = new Task(name, description, id, Status.NEW);
        tasks.put(id, task);
        id++;
        return task;
    }

    @Override
    public Epic createEpic(String name, String description) {
        Epic epic = new Epic(name, description, id, Status.NEW);
        epics.put(id, epic);
        id++;
        return epic;
    }

    @Override
    public Subtask createSubTask(String name, String description, int epicId) {
        Subtask subtask = new Subtask(name, description, id, Status.NEW, epicId);
        subTasks.put(id, subtask);
        epics.get(epicId).getArraySubtasks().add(subtask);
        id++;
        return subtask;
    }

    @Override
    public HashMap getAllTasks() {
        return tasks;
    }

    @Override
    public HashMap getAllEpics() {
        return epics;
    }

    @Override
    public HashMap getAllSubTasks() {
        return subTasks;
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteSubTaskById(int id) {
        subTasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        if (!epics.get(id).getArrayOfSubtasksByEpic().isEmpty()) {
            int findSubtask = epics.get(id).getIdOfEpic();
            for (Subtask subtask : epics.get(id).getArrayOfSubtasksByEpic()) {
                if (subtask.getEpicIdOfSubtask() == findSubtask) {
                    subTasks.remove(subtask.getIdOfSubtask());
                }
            }
        }
        epics.remove(id);
    }

    @Override
    public void deleteAll() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    @Override
    public Subtask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    @Override
    public ArrayList getAllSubTasksByEpic(Epic epic) {
        return epic.getArraySubtasks();
    }

    @Override
    public void updateTask(int id) {
        if (tasks.containsKey(tasks.get(id))) {
            tasks.put(id, tasks.get(id));
        }
    }

    @Override
    public void updateEpic(int id) {
        if (tasks.containsKey(epics.get(id))) {
            tasks.put(id, epics.get(id));
        }
    }

    @Override
    public void updateSubTask(int id) {
        if (tasks.containsKey(subTasks.get(id))) {
            tasks.put(id, subTasks.get(id));
        }
    }

    private void updateStatusOfEpic(int id) {
        if (epics.get(id).getArraySubtasks().isEmpty() == true) {
            epics.get(id).setEpicStatus(Status.NEW);
        } else if (!epics.get(id).getArraySubtasks().isEmpty()) {
            int doneTask = 0;
            int toDoTask = subTasks.size();
            for (Subtask subtask : epics.get(id).getArrayOfSubtasksByEpic()) {
                if (subtask.getStatusSubtask() == (Status.DONE)) {
                    doneTask++;
                }
            }
            if (doneTask == toDoTask) {
                epics.get(id).setEpicStatus(Status.DONE);
            } else epics.get(id).setEpicStatus(Status.IN_PROGRESS);
        }
    }

    private void changeSubtaskStatus(int id, Status status) {
        subTasks.get(id).setStatusSubtask(Status.DONE);
    }

    @Override
    public Epic getEpic(int id) {
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        return subTasks.get(id);
    }

    @Override
    public Task getTask(int id) {
        return tasks.get(id);
    }

}