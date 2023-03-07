package service;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HistoryManagerTest {

    private final HistoryManager historyManager = new InMemoryHistoryManager();

    @Test
    void shouldAddHistory() {
        Task task = new Task("Задача№1", "ОписаниеЗадача№1", 1, Status.NEW, 1, "2029-12-21T21:21:21");
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size(), "Задача не добавлена");
    }

    @Test
    void shouldRemove() {
        Task task = new Task("Задача№1", "ОписаниеЗадача№1", 1, Status.NEW, 1, "2029-12-21T21:21:21");
        Task task2 = new Task("Задача№1", "ОписаниеЗадача№1", 2, Status.NEW, 1, "2029-12-21T21:21:21");
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.remove(historyManager.getNodeById(1), task);
        assertEquals(1, historyManager.getHistory().size(), "Что-то не удалилось");
        assertNotNull(historyManager.getHistory(), "Удалилось всё(((");
    }

    @Test
    void shouldGetHistory() {
        Task task = new Task("Задача№1", "ОписаниеЗадача№1", 1, Status.NEW, 1, "2029-12-21T21:21:21");
        Task task2 = new Task("Задача№1", "ОписаниеЗадача№1", 2, Status.NEW, 1, "2029-12-21T21:21:21");
        historyManager.add(task);
        historyManager.add(task2);
        assertEquals(List.of(task, task2).size(), historyManager.getHistory().size());
    }

    @Test
    void shouldGetHistoryHash() {
        Task task = new Task("Задача№1", "ОписаниеЗадача№1", 1, Status.NEW, 1, "2029-12-21T21:21:21");
        Task task2 = new Task("Задача№1", "ОписаниеЗадача№1", 2, Status.NEW, 1, "2029-12-21T21:21:21");
        historyManager.add(task);
        historyManager.add(task2);
        assertEquals(2, historyManager.getHistoryHash().size());
    }

    @Test
    void shouldReturnEmptyHistory() {
        Task task = new Task("Задача№1", "ОписаниеЗадача№1", 1, Status.NEW, 1, "2029-12-21T21:21:21");
        Task task2 = new Task("Задача№1", "ОписаниеЗадача№1", 2, Status.NEW, 1, "2029-12-21T21:21:21");
        historyManager.add(task);
        historyManager.add(task2);
        assertNotNull(historyManager.getHistoryHash(), "История задач пустует");
    }

    @Test
    void shouldDeleteFromHistory() {
        Task task = new Task("Задача№1", "ОписаниеЗадача№1", 1, Status.NEW, 1, "2029-12-21T21:21:21");
        Task task2 = new Task("Задача№1", "ОписаниеЗадача№1", 2, Status.NEW, 1, "2029-12-21T21:21:21");
        Task task3 = new Task("Задача№1", "ОписаниеЗадача№1", 3, Status.NEW, 1, "2029-12-21T21:21:21");
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