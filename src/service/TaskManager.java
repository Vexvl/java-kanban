package service;

import model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public interface TaskManager {

    void createTask(String name, String description, int minutesToDo, String startTime) throws ManagerSaveException, IOException;

    void createEpic(String name, String description) throws ManagerSaveException, IOException;

    void createSubTask(String name, String description, int epicId, int minutesToDo, String startTime) throws ManagerSaveException, IOException;

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

    TreeSet<Task> getPrioritizedTasks();

    boolean IfIntersection(Task task) throws IntersectionException;

}