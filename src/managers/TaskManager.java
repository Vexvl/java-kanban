package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public interface TaskManager {
    Task createTask(String name, String description);

    Epic createEpic(String name, String description);

    Subtask createSubTask(String name, String description, int epicId);

    void deleteTaskById(int id);

    void deleteSubTaskById(int id);

    void deleteEpicById(int id);

    void deleteAll();

    ArrayList getAllSubTasksByEpic(Epic epic);

    void updateTask(int id);

    void updateEpic(int id);

    void updateSubTask(int id);

    void updateStatusOfEpic(int id);

    void changeStatusSubtasktoDone(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    Task getTask(int id);

}