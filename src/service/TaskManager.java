package service;

import exception.IntersectionException;
import exception.ManagerSaveException;
import model.Epic;
import model.Subtask;
import model.Task;
import server.KVTaskClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TaskManager {

    void createTask(String name, String description, int duration, String startTime) throws ManagerSaveException, IOException, InterruptedException;

    void createEpic(String name, String description) throws ManagerSaveException, IOException, InterruptedException;

    void createSubTask(String name, String description, int epicId, int duration, String startTime) throws ManagerSaveException, IOException, InterruptedException;

    int getId();

    void updateTask(Task task) throws ManagerSaveException, IOException, InterruptedException;

    void updateEpic(Epic epic) throws ManagerSaveException, IOException, InterruptedException;

    void updateSubtask(Subtask subtask) throws ManagerSaveException, IOException, InterruptedException;

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

    void updateStatusOfEpic(Epic epic);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    Task getTask(int id);

    Set<Task> getPrioritizedTasks();

    boolean IfIntersection(Task task) throws IntersectionException;

    KVTaskClient getKVClient() throws IOException, InterruptedException;
}