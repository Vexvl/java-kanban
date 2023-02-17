package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager {
    Task createTask(String name, String description);

    Epic createEpic(String name, String description, int epicId);

    Subtask createSubTask(String name, String description, int epicId);

    HashMap getAllTasks();

    HashMap getAllEpics();

    HashMap getAllSubTasks();

    void deleteTaskById(int id);

    void deleteSubTaskById(int id);

    void deleteEpicById(int id);

    void deleteAll();

    Task getTaskById(int id);

    Task getSimpleTask(int id);

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