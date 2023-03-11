package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import server.KVServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTest {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
    private static HttpTaskServer httpTaskServer;
    private static HttpClient client;
    private static HttpRequest.Builder requestBuilder;
    private static URI uri;
    private static HttpRequest request;
    private static HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
    private static HttpResponse<String> response;

    @BeforeAll
    public static void beforeAll() throws IOException {
        new KVServer().start();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.stopServer();
        requestBuilder = HttpRequest.newBuilder();
    }

    @BeforeEach
    public void beforeEach() {
        client = HttpClient.newHttpClient();
        requestBuilder = HttpRequest.newBuilder().uri(URI.create("http://localhost:8090"));
    }


    @Test
    public void testGetAllTasks() throws IOException, InterruptedException {
        Task savedTask = new Task("Задача№1", "ОписаниеЗадача№1", 1, Status.NEW, 22, "2026-12-21T21:21:21");
        String taskJson = gson.toJson(savedTask);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder.uri(uri).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build(), handler);

        Epic savedEpic = new Epic("Эпик1", "ОписаниеЭпик№1", 2, Status.NEW);
        String epicJson = gson.toJson(savedEpic);

        uri = URI.create("http://localhost:8090/tasks/epic/");
        response = client.send(requestBuilder.uri(uri).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build(), handler);

        Subtask savedSubtask = new Subtask("Подзадача1", "ОписаниеПодзадача1", 3, Status.NEW, 2, 22, "2026-12-21T21:21:21");
        String subtaskJson = gson.toJson(savedSubtask);

        uri = URI.create("http://localhost:8090/tasks/subtask/");
        response = client.send(requestBuilder.uri(uri).POST(HttpRequest.BodyPublishers.ofString(subtaskJson)).build(), handler);

        uri = URI.create("http://localhost:8090/tasks/");
        request = requestBuilder.GET().uri(uri).build();
        response = client.send(request, handler);

        assertEquals(200, response.statusCode());

    }

    @Test
    public void testGetAndPostTask() throws IOException, InterruptedException {
        Task savedTask = new Task("Задача№1", "ОписаниеЗадача№1", 1, Status.NEW, 22, "2026-12-21T21:21:21");
        String taskJson = gson.toJson(savedTask);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder.uri(uri).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build(), handler);

        assertEquals(200, response.statusCode());

        int id = Integer.parseInt(response.body());

        uri = URI.create("http://localhost:8090/tasks/task/?id=" + id);
        response = client.send(requestBuilder.GET().uri(uri).build(), handler);

        taskJson = gson.toJson(savedTask);

        assertEquals(200, response.statusCode());
        assertEquals(taskJson, response.body());

    }

    @Test
    public void testGetAndPostEpic() throws IOException, InterruptedException {
        Epic savedEpic = new Epic("Эпик1", "ОписаниеЭпик№1", 1, Status.NEW);
        String epicJson = gson.toJson(savedEpic);

        uri = URI.create("http://localhost:8090/tasks/epic/");
        response = client.send(requestBuilder.uri(uri).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build(), handler);

        assertEquals(200, response.statusCode());

        int id = Integer.parseInt(response.body());

        uri = URI.create("http://localhost:8090/tasks/epic/?id=" + id);
        response = client.send(requestBuilder.GET().uri(uri).build(), handler);

        epicJson = gson.toJson(savedEpic);

        assertEquals(epicJson, response.body(), "Задачи не совпадают");

    }

    @Test
    public void testGetAndPostSubtask() throws IOException, InterruptedException {
        Epic savedEpic = new Epic("Эпик1", "ОписаниеЭпик№1", 1, Status.NEW);
        String epicJson = gson.toJson(savedEpic);

        uri = URI.create("http://localhost:8090/tasks/epic/");
        response = client.send(requestBuilder.uri(uri).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build(), handler);

        assertEquals(200, response.statusCode());

        Subtask savedSubtask = new Subtask("Подзадача1", "ОписаниеПодзадача1", 2, Status.NEW, 1, 22, "2026-12-21T21:21:21");
        String subtaskJson = gson.toJson(savedSubtask);

        uri = URI.create("http://localhost:8090/tasks/subtask/");
        handler = HttpResponse.BodyHandlers.ofString();
        response = client.send(requestBuilder.uri(uri).POST(HttpRequest.BodyPublishers.ofString(subtaskJson)).build(), handler);

        assertEquals(200, response.statusCode());
    }

    @Test
    public void testGetAndDeleteTasks() throws IOException, InterruptedException {

        Task savedTask = new Task("Задача№1", "ОписаниеЗадача№1", 1, Status.NEW, 22, "2026-12-21T21:21:21");
        String taskJson = gson.toJson(savedTask);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder.uri(uri).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build(), handler);

        uri = URI.create("http://localhost:8090/tasks/task/");
        request = requestBuilder.DELETE().uri(uri).build();
        response = client.send(request, handler);

        uri = URI.create("http://localhost:8090/tasks/task/");
        request = requestBuilder.GET().uri(uri).build();
        response = client.send(request, handler);

        assertEquals(2, response.body().length(), "Удаление задач не выполнено");

        Task savedTask2 = new Task("Задача№1", "ОписаниеЗадача№1", 2, Status.NEW, 22, "2026-12-21T21:21:21");
        taskJson = gson.toJson(savedTask2);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder.uri(uri).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build(), handler);

        uri = URI.create("http://localhost:8090/tasks/task/");
        request = requestBuilder.GET().uri(uri).build();
        response = client.send(request, handler);

        assertEquals(200, response.statusCode());
        assertNotEquals(2, response.body().length());
    }


    @Test
    public void testGetHistory() throws IOException, InterruptedException {
        Task task = new Task("Задача№1", "ОписаниеЗадача№1", 1, Status.NEW, 1, "2029-12-21T21:21:21");
        String taskJson = gson.toJson(task);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder.uri(uri).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build(), handler);
        int id = Integer.parseInt(response.body());

        uri = URI.create("http://localhost:8090/tasks/task/?id=" + id);
        request = requestBuilder.GET().uri(uri).build();
        response = client.send(request, handler);

        uri = URI.create("http://localhost:8090/tasks/history/");
        request = requestBuilder.GET().uri(uri).build();
        response = client.send(request, handler);

        assertEquals(200, response.statusCode());
    }


    @Test
    public void testDeleteTaskByID() throws IOException, InterruptedException {
        Task task = new Task("Задача№1", "ОписаниеЗадача№1", 1, Status.NEW, 1, "2029-12-21T21:21:21");
        String taskJson = gson.toJson(task);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder.uri(uri).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build(), handler);

        assertEquals(200, response.statusCode());

        int id = Integer.parseInt(response.body());

        uri = URI.create("http://localhost:8090/tasks/task/?id=" + id);
        response = client.send(requestBuilder.uri(uri).DELETE().build(), handler);

        uri = URI.create("http://localhost:8090/tasks/task/?id=" + id);
        response = client.send(requestBuilder.uri(uri).GET().build(), handler);

        assertTrue(response.body().isEmpty());
    }

    @Test
    public void testDeleteEpicByID() throws IOException, InterruptedException {
        Epic savedEpic = new Epic("Эпик1", "ОписаниеЭпик№1", 1, Status.NEW);
        String epicJson = gson.toJson(savedEpic);

        uri = URI.create("http://localhost:8090/tasks/epic/");
        response = client.send(requestBuilder.uri(uri).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build(), handler);

        assertEquals(200, response.statusCode(), "Запрос не обработан");
        assertFalse(response.body().isEmpty(), "Задача не сохранена, не возвращен id");
        int id = Integer.parseInt(response.body());

        uri = URI.create("http://localhost:8090/tasks/epic/?id=" + id);
        response = client.send(requestBuilder.uri(uri).DELETE().build(), handler);

        uri = URI.create("http://localhost:8090/tasks/epic/?id=" + id);
        response = client.send(requestBuilder.uri(uri).GET().build(), handler);

        assertEquals(404, response.statusCode());
        assertTrue(response.body().isEmpty());
    }

    static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
            jsonWriter.value(localDateTime.toString());
        }

        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString());
        }
    }
}
