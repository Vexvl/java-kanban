package service;

import model.Epic;
import model.ManagerSaveException;
import model.Subtask;
import model.Task;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TaskManager {

    void createTask(String name, String description) throws ManagerSaveException, IOException;

    void createEpic(String name, String description) throws ManagerSaveException, IOException;

    void createSubTask(String name, String description, int epicId) throws ManagerSaveException, IOException;

    Map<Integer, Task> getAllTypeTasks();

    Map<Integer, Epic> getAllEpics();

    Map<Integer, Subtask> getAllSubTasks();

    Map<Integer, Task> getAllTasks();

    void deleteTaskById(int id);

    void deleteSubTaskById(int id);

    void deleteEpicById(int id);

    void deleteAll();

    Task getTaskById(int id);

    Subtask getSubTaskById(int id);

    Epic getEpicById(int id);

    List<Subtask> getSubtasksByEpic(Epic epic);

    void updateTask(int id);

    void updateEpic(int id);

    void updateSubTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    Task getTask(int id);

}