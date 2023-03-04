package service;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTests<T extends TaskManager> {

    private TaskManager taskManager = new InMemoryTaskManager();

    @Test
    public void createTask() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 22, "2026-12-21T21:21:21");

        final Task savedTask = new Task("Задача№1", "ОписаниеЗадача№1", 1, Status.NEW, 22, "2026-12-21T21:21:21");

        assertNotNull(taskManager.getTaskById(1), "Задача не найдена");
        assertEquals(savedTask, taskManager.getTask(1), "Задачи не совпадают");

        final Map<Integer, Task> tasks = taskManager.getAllTasks();
        assertNotNull(tasks, "Задачи не возвращаются");
        assertEquals(1, tasks.size(), "Неверное количество задач");
        assertEquals(savedTask, tasks.get(1), "Задачи не совпали");
    }

    @Test
    public void createEpic() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");

        final Epic savedEpic = new Epic("Эпик1", "ОписаниеЭпик№1", 1, Status.NEW);

        assertNotNull(taskManager.getEpicById(1), "Эпик не найден");
        assertEquals(savedEpic, taskManager.getEpicById(1), "Эпики не совпадают");

        final Map<Integer, Epic> epics = taskManager.getAllEpics();
        assertNotNull(epics, "Эпики не возвращаются");
        assertEquals(1, epics.size(), "Неверное количество эпиков");
        assertEquals(savedEpic, epics.get(1), "Эпики не совпали");
    }

    @Test
    public void createSubTask() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 22, "2026-12-21T21:21:21");

        final Subtask savedSubtask = new Subtask("Подзадача1", "ОписаниеПодзадача1", 2, Status.NEW, 1, 22, "2026-12-21T21:21:21");

        assertNotNull(taskManager.getSubTaskById(2), "Подзадача не найдена");
        assertEquals(savedSubtask, taskManager.getSubTaskById(2), "Подзадачи не совпадают");

        final Map<Integer, Subtask> subtasks = taskManager.getAllSubTasks();
        assertNotNull(subtasks, "Подзадача не возвращается");
        assertEquals(1, subtasks.size(), "Неверное количество подзадач");
        assertEquals(savedSubtask, subtasks.get(2), "Подзадачи не совпали");
    }

    @Test
    public void getAllTypeTasks() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 22, "2026-12-21T21:21:21");
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 2, 22, "2026-12-21T21:21:21");
        assertNotNull(taskManager.getAllTypeTasks(), "Задачи не возвращаются");
        assertEquals(3, taskManager.getAllTypeTasks().size(), "Неверное количество задач");
    }

    @Test
    public void getAllTypeTasksNull() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 22, "2026-12-21T21:21:21");
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 2, 22, "2026-12-21T21:21:21");
        taskManager.deleteAll();
        assertEquals(0, taskManager.getAllEpics().size(), "Задачи возвращаются");
    }

    @Test
    public void getAllEpics() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createEpic("Эпик2", "ОписаниеЭпик№2");
        assertNotNull(taskManager.getAllEpics(), "Эпики не возвращаются");
        assertEquals(2, taskManager.getAllEpics().size(), "Неверное количество эпиков");
    }

    @Test
    public void getAllEpicsNull() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createEpic("Эпик2", "ОписаниеЭпик№2");
        taskManager.getAllEpics().clear();
        assertEquals(0, taskManager.getAllEpics().size(), "Эпики возвращаются");
    }

    @Test
    public void getAllSubTasks() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 22, "2026-12-21T21:21:21");
        taskManager.createSubTask("Подзадача2", "ОписаниеПодзадача2", 1, 22, "2026-12-21T21:21:21");
        assertNotNull(taskManager.getAllSubTasks(), "Подзадачи не возвращаются");
        assertEquals(2, taskManager.getAllSubTasks().size(), "Неверное количество подзадач");
    }

    @Test
    public void getAllSubTasksNull() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 22, "2026-12-21T21:21:21");
        taskManager.createSubTask("Подзадача2", "ОписаниеПодзадача2", 1, 22, "2026-12-21T21:21:21");
        taskManager.getAllSubTasks().clear();
        assertEquals(0, taskManager.getAllSubTasks().size(), "Неверное количество подзадач");
    }

    @Test
    public void getAllTasks() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 22, "2026-12-21T21:21:21");
        taskManager.createTask("Задача№2", "ОписаниеЗадача№2", 22, "2026-12-21T21:21:21");
        assertNotNull(taskManager.getAllTasks(), "Задачи не возвращаются");
        assertEquals(2, taskManager.getAllTasks().size(), "Неверное количество задач");
    }

    @Test
    public void getAllTasksNull() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 22, "2026-12-21T21:21:21");
        taskManager.createTask("Задача№2", "ОписаниеЗадача№2", 22, "2026-12-21T21:21:21");
        taskManager.getAllTasks().clear();
        assertEquals(0, taskManager.getAllTasks().size(), "Неверное количество задач");
    }

    @Test
    public void deleteTaskById() throws ManagerSaveException, IOException {
        int id = 1;
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 22, "2026-12-21T21:21:21");
        taskManager.createTask("Задача№2", "ОписаниеЗадача№2", 22, "2026-12-21T21:21:21");
        taskManager.deleteTaskById(id);
        assertNotNull(taskManager.getAllTasks(), "Метод удаления работает неправильно");
        assertEquals(1, taskManager.getAllTasks().size(), "Задача не удалилась");
    }

    @Test
    public void deleteTaskByWrongId() throws ManagerSaveException, IOException {
        int id = 3;
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 22, "2026-12-21T21:21:21");
        taskManager.createTask("Задача№2", "ОписаниеЗадача№2", 22, "2026-12-21T21:21:21");
        taskManager.deleteTaskById(id);
        assertNotNull(taskManager.getAllTasks(), "Метод удаления работает неправильно");
        assertEquals(2, taskManager.getAllTasks().size(), "Задача не удалилась");
    }

    @Test
    public void deleteSubTaskById() throws ManagerSaveException, IOException {
        int id = 1;
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 22, "2026-12-21T21:21:21");
        taskManager.createSubTask("Подзадача2", "ОписаниеПодзадача2", 1, 22, "2026-12-21T21:21:21");
        taskManager.deleteSubTaskById(id);
        assertNotNull(taskManager.getAllSubTasks(), "Метод удаления работает неправильно");
        assertEquals(2, taskManager.getAllSubTasks().size(), "Количество подзадач не совпадает");
    }

    @Test
    public void deleteSubTaskByWrongId() throws ManagerSaveException, IOException {
        int id = 3;
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 22, "2026-12-21T21:21:21");
        taskManager.createSubTask("Подзадача2", "ОписаниеПодзадача2", 1, 22, "2026-12-21T21:21:21");
        assertNotNull(taskManager.getAllSubTasks(), "Метод удаления работает неправильно");
        assertNotEquals(id, taskManager.getAllSubTasks().size(), "Количество подзадач совпадает");
    }

    @Test
    public void deleteEpicById() throws ManagerSaveException, IOException {
        int id = 1;
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createEpic("Эпик2", "ОписаниеЭпик№2");
        taskManager.deleteEpicById(id);
        assertNotNull(taskManager.getAllEpics(), "Метод удаления работает неправильно");
        assertEquals(1, taskManager.getAllEpics().size(), "Количество эпиков не совпадает");
    }

    @Test
    public void deleteEpicByWrongId() throws ManagerSaveException, IOException {
        int id = 3;
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createEpic("Эпик2", "ОписаниеЭпик№2");
        assertNotNull(taskManager.getAllEpics(), "Метод удаления работает неправильно");
        assertNotEquals(id, taskManager.getAllEpics().size(), "Количество эпиков не совпадает");
    }


    @Test
    public void deleteAll() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 22, "2026-12-21T21:21:21");
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 22, "2026-12-21T21:21:21");
        taskManager.deleteAll();
        assertEquals(0, taskManager.getAllTasks().size(), "Метод не удаляет все задачи");
    }

    @Test
    public void getSubtasksByEpic() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 22, "2026-12-21T21:21:21");
        taskManager.createSubTask("Подзадача2", "ОписаниеПодзадача2", 1, 22, "2026-12-21T21:21:21");
        Subtask subtask = taskManager.getSubtask(2);
        Subtask subtask2 = taskManager.getSubtask(3);
        Epic epic = taskManager.getEpic(1);
        assertEquals(epic.getSubtasks(), List.of(subtask, subtask2), "Подзадачи не равны");
    }

    @Test
    public void getEpic() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        assertEquals(taskManager.getEpic(1), taskManager.getAllEpics().get(1), "Эпики не совпадают");
    }

    @Test
    public void getEpicByWrongId() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        assertNotEquals(taskManager.getEpic(3), taskManager.getAllEpics().get(1), "Эпики совпадают");
    }

    @Test
    public void getSubtask() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 22, "2026-12-21T21:21:21");
        assertEquals(taskManager.getSubtask(2), taskManager.getAllSubTasks().get(2), "Не нашли подзадачу");
    }

    @Test
    public void getSubtaskWrong() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 22, "2026-12-21T21:21:21");
        assertNotEquals(taskManager.getSubtask(3), taskManager.getAllSubTasks().get(2), "Нашли подзачачу");
    }

    @Test
    public void getTask() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 22, "2026-12-21T21:21:21");
        assertEquals(taskManager.getTask(1), taskManager.getAllTasks().get(1), "Не нашли задачу");
    }

    @Test
    public void getTaskWrong() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 22, "2026-12-21T21:21:21");
        assertNotEquals(taskManager.getTask(3), taskManager.getAllTasks().get(1), "Нашли задачу");
    }

}