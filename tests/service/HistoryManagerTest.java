package service;

import exceptions.ManagerSaveException;
import model.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HistoryManagerTest {

    CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();
    private TaskManager taskManager = new InMemoryTaskManager();
    private final HistoryManager historyManager = new InMemoryHistoryManager();

    @Test
    void add() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 240, "2029-12-21T21:21:21");
        Task task = taskManager.getTaskById(1);
        customLinkedList.linkLast(task, task.getIdOfTask());
        assertEquals(1, customLinkedList.getNodesById().size(), "Задача не добавлена");
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
        assertEquals(1, customLinkedList.getNodesById().size(), "Что-то не удалилось");
        assertNotNull(customLinkedList.getNodesById(), "Удалилось всё(((");
    }

    @Test
    void getHistory() throws ManagerSaveException, IOException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 240, "2029-12-21T21:21:21");
        taskManager.createTask("Задача№2", "ОписаниеЗадача№2", 240, "2029-12-21T21:21:21");
        Task task = taskManager.getTaskById(1);
        Task task2 = taskManager.getTaskById(2);
        customLinkedList.linkLast(task, task.getIdOfTask());
        customLinkedList.linkLast(task2, task2.getIdOfTask());
        assertEquals(List.of(task,task2).size(), customLinkedList.getHistory().size());
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
        customLinkedList.linkLast(task, task.getIdOfTask());
        assertEquals(customLinkedList.getNodesById().get(1), customLinkedList.getHistoryHash().get(1));
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
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        double i = 1.00 / historyManager.getHistory().size();
        double randomInt = Math.random();
        if (randomInt < i) {
            historyManager.remove(historyManager.getNodeById(1), task);
            assertEquals(2, historyManager.getHistoryHash().size());
            System.out.println("Удалено с начала");
        }
        if (randomInt == i) {
            historyManager.remove(historyManager.getNodeById(2), task2);
            assertEquals(2, historyManager.getHistoryHash().size());
            System.out.println("Удалено из середины");
        }
        if (randomInt > i) {
            historyManager.remove(historyManager.getNodeById(3), task3);
            assertEquals(2, historyManager.getHistoryHash().size());
            System.out.println("Удалено с конца");
        }

    }

}