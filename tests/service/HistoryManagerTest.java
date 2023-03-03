package service;

import model.ManagerSaveException;
import model.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();
    private TaskManager taskManager = new InMemoryTaskManager();

    @Test
    void add() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 240, "2029-12-21T21:21:21");
        Task task = taskManager.getTaskById(1);
        customLinkedList.linkLast(task, task.getIdOfTask());
        assertEquals(1, customLinkedList.getNodesById().size());
    }

    @Test
    void remove() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 240, "2029-12-21T21:21:21");
        taskManager.createTask("Задача№2", "ОписаниеЗадача№2", 240, "2029-12-21T21:21:21");
        Task task = taskManager.getTaskById(1);
        Task task2 = taskManager.getTaskById(2);
        customLinkedList.linkLast(task, task.getIdOfTask());
        customLinkedList.linkLast(task2, task2.getIdOfTask());
        customLinkedList.removeNode(customLinkedList.getNodeById(1), task);
        assertEquals(1, customLinkedList.getNodesById().size());
        assertNotNull(customLinkedList.getNodesById());
    }

    @Test
    void getHistory() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 240, "2029-12-21T21:21:21");
        taskManager.createTask("Задача№2", "ОписаниеЗадача№2", 240, "2029-12-21T21:21:21");
        Task task = taskManager.getTaskById(1);
        Task task2 = taskManager.getTaskById(2);
        customLinkedList.linkLast(task, task.getIdOfTask());
        customLinkedList.linkLast(task2, task2.getIdOfTask());
        assertEquals(List.of(task, task2), customLinkedList.getHistory());
    }

    @Test
    void getHistoryHash() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 240, "2029-12-21T21:21:21");
        taskManager.createTask("Задача№2", "ОписаниеЗадача№2", 240, "2029-12-21T21:21:21");
        Task task = taskManager.getTaskById(1);
        Task task2 = taskManager.getTaskById(2);
        customLinkedList.linkLast(task, task.getIdOfTask());
        customLinkedList.linkLast(task2, task2.getIdOfTask());
        assertEquals(2, customLinkedList.getHistoryHash().size());
    }

    @Test
    void getNodeById() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 240, "2029-12-21T21:21:21");
        Task task = taskManager.getTaskById(1);
        Task task2 = taskManager.getTaskById(2);
        customLinkedList.linkLast(task, task.getIdOfTask());
        customLinkedList.linkLast(task2, task2.getIdOfTask());
        assertEquals(customLinkedList.getNodesById().get(1), customLinkedList.getHistoryHash().get(1));
    }

    @Test
    void duplicate() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 240, "2029-12-21T21:21:21");
        taskManager.createTask("Задача№2", "ОписаниеЗадача№2", 240, "2029-12-21T21:21:21");
        Task task = taskManager.getTaskById(1);
        Task task2 = taskManager.getTaskById(2);
        customLinkedList.linkLast(task, task.getIdOfTask());
        customLinkedList.linkLast(task2, task2.getIdOfTask());
        Set<CustomLinkedList.Node> setOfNodes = new HashSet<>((Collection) customLinkedList.getHistoryHash());
        assertEquals(setOfNodes.size(), customLinkedList.getHistoryHash().size(), "Найден дубликат");
    }

    @Test
    void emptyHistory() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 240, "2029-12-21T21:21:21");
        taskManager.createTask("Задача№2", "ОписаниеЗадача№2", 240, "2029-12-21T21:21:21");
        Task task = taskManager.getTaskById(1);
        Task task2 = taskManager.getTaskById(2);
        customLinkedList.linkLast(task, task.getIdOfTask());
        customLinkedList.linkLast(task2, task2.getIdOfTask());
        assertNotNull(customLinkedList.getHistoryHash(), "История задач пустует");
    }

    @Test
    void deleteFromHistory() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 240, "2029-12-21T21:21:21");
        taskManager.createTask("Задача№2", "ОписаниеЗадача№2", 240, "2029-12-21T21:21:21");
        taskManager.createTask("Задача№3", "ОписаниеЗадача№3", 240, "2029-12-21T21:21:21");
        Task task = taskManager.getTaskById(1);
        Task task2 = taskManager.getTaskById(2);
        Task task3 = taskManager.getTaskById(3);
        customLinkedList.linkLast(task, task.getIdOfTask());
        customLinkedList.linkLast(task2, task2.getIdOfTask());
        customLinkedList.linkLast(task3, task3.getIdOfTask());
        double randomInt = Math.random();
        if (randomInt < 1 / customLinkedList.getHistoryHash().size()) {
            customLinkedList.removeNode(customLinkedList.getNodeById(1), taskManager.getTask(1));
            assertEquals(2, customLinkedList.getHistoryHash().size());
            System.out.println("Удалено сначала");
        }
        if (randomInt == 1 / customLinkedList.getHistoryHash().size()) {
            customLinkedList.removeNode(customLinkedList.getNodeById(customLinkedList.getHistoryHash().size() / 2), taskManager.getTask(customLinkedList.getHistoryHash().size() / 2));
            assertEquals(2, customLinkedList.getHistoryHash().size());
            System.out.println("Удалено из середины");
        }
        if (randomInt > 1 / customLinkedList.getHistoryHash().size()) {
            customLinkedList.removeNode(customLinkedList.getNodeById(customLinkedList.getHistoryHash().size()), taskManager.getTask(customLinkedList.getHistoryHash().size()));
            assertEquals(2, customLinkedList.getHistoryHash().size());
            System.out.println("Удалено с конца");
        }

    }

}