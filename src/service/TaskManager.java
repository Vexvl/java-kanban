package service;

import model.Epic;
import model.ManagerSaveException;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager {

    void createTask(String name, String description) throws ManagerSaveException;

    void createEpic(String name, String description) throws ManagerSaveException;

    void createSubTask(String name, String description, int epicId) throws ManagerSaveException;

    HashMap<Integer, Task> getEverything();

    HashMap getAllEpics();

    HashMap getAllSubTasks();

    HashMap getAllTasks();

    void deleteTaskById(int id);

    void deleteSubTaskById(int id);

    void deleteEpicById(int id);

    void deleteAll();

    Task getTaskById(int id);

    Subtask getSubTaskById(int id);

    Epic getEpicById(int id);

    ArrayList getAllSubTasksByEpic(Epic epic);

    void updateTask(int id);

    void updateEpic(int id);

    void updateSubTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    Task getTask(int id);

}