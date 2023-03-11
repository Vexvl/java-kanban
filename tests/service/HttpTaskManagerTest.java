package service;

import exceptions.ManagerSaveException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.KVServer;
import server.KVTaskClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {

    private KVTaskClient client;
    private KVServer server;

    @BeforeEach
    public void beforeEach() throws IOException, InterruptedException {
        server = new KVServer();
        server.start();
        taskManager = new HttpTaskManager("http://localhost:8078");
    }

    @Test
    public void shouldSaveTasks() throws IOException, InterruptedException, ManagerSaveException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 1, "2029-12-21T21:21:21");
        taskManager.createEpic("Эпик1", "ОписаниеЭпик1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 3, 3, "2056-12-21T21:21:21");
        client = taskManager.getKVClient();
        String savedTasks = client.load("tasks");
        String savedEpics = client.load("epics");
        String savedSubtasks = client.load("subtasks");
        String savedHistory = client.load("history");

        assertNotEquals(0, savedTasks.length(), "Задачи не сохранены");
        assertNotEquals(0, savedEpics.length(), "Задачи не сохранены");
        assertNotEquals(0, savedSubtasks.length(), "Задачи не сохранены");
        assertNotEquals(0, savedHistory.length(), "Истории нет");
    }

    @Test
    public void shouldLoadFromServer() throws IOException, InterruptedException, ManagerSaveException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 1, "2029-12-21T21:21:21");
        taskManager.createEpic("Эпик1", "ОписаниеЭпик1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 3, 3, "2056-12-21T21:21:21");
        HttpTaskManager loader = HttpTaskManager.load("http://localhost:8078");
        client = loader.getKVTaskClient();
        String savedTasks = client.load("tasks");
        String savedEpics = client.load("epics");
        String savedSubtasks = client.load("subtasks");
        String savedHistory = client.load("history");

        assertNotEquals(0, savedTasks.length(), "Задачи не сохранены");
        assertNotEquals(0, savedEpics.length(), "Задачи не сохранены");
        assertNotEquals(0, savedSubtasks.length(), "Задачи не сохранены");
        assertNotEquals(0, savedHistory.length(), "Истории нет");
    }

    @AfterEach
    public void afterEach() {
        server.stop();
    }
}
