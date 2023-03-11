package service;

import exceptions.ManagerSaveException;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void setTaskManager() {
        this.taskManager = new InMemoryTaskManager();
    }

    @Test
    public void createTask() throws ManagerSaveException, IOException, InterruptedException {
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
    public void createEpic() throws ManagerSaveException, IOException, InterruptedException {
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
    public void createSubTask() throws ManagerSaveException, IOException, InterruptedException {
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
    public void updateTask() throws ManagerSaveException, IOException, InterruptedException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 22, "2026-12-21T21:21:21");
        final Task savedTask = new Task("Задача№1", "ОписаниеЗадача2", 1, Status.NEW, 22, "2026-12-21T21:21:21");
        taskManager.updateTask(savedTask);
        assertEquals("ОписаниеЗадача2", taskManager.getAllTasks().get(1).getDescription());
    }

    @Test
    public void updateEpic() throws ManagerSaveException, IOException, InterruptedException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        final Epic epic = new Epic("Задача№1", "ОписаниеЗадача2", 1, Status.NEW);
        taskManager.updateEpic(epic);
        assertEquals("ОписаниеЗадача2", taskManager.getAllEpics().get(1).getDescription());
    }

    @Test
    public void updateSubtask() throws ManagerSaveException, IOException, InterruptedException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 22, "2026-12-21T21:21:21");
        final Subtask savedSubtask = new Subtask("Подзадача1", "ОписаниеПодЗадача2", 2, Status.NEW, 1, 22, "2026-12-21T21:21:21");
        taskManager.updateSubtask(savedSubtask);
        assertEquals("ОписаниеПодЗадача2", taskManager.getAllSubTasks().get(2).getDescription());
    }

}