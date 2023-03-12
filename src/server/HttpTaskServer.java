package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import exceptions.ManagerSaveException;
import model.Epic;
import model.Subtask;
import model.Task;
import service.FileBackedTasksManager;
import service.Managers;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private static final String PATH = "tests/resources/historyTest.txt";
    private final FileBackedTasksManager tasksManager;
    private final HttpServer server;
    private final Gson gson;
    private static final int SECOND_PARAMETR_IN_REQUESTED_URL_INDEX = 2;

    public HttpTaskServer() throws IOException {
        server = HttpServer.create();
        server.bind(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TasksHandler());
        tasksManager = Managers.getFileManager(PATH);
        server.start();
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer server = new HttpTaskServer();
    }

    public void startServer() {
        server.start();
    }

    public void stopServer() {
        server.stop(3);
    }

    private void handlePosts(HttpExchange httpExchange) throws IOException, ManagerSaveException, InterruptedException {
        InputStream inputStream = httpExchange.getRequestBody();
        String bodyJson = new String(inputStream.readAllBytes(), UTF_8);
        String urlToString = httpExchange.getRequestURI().toString();
        String[] splitUrl = urlToString.split("/");
        switch (splitUrl[SECOND_PARAMETR_IN_REQUESTED_URL_INDEX]) {
            case "task":
                Task task = gson.fromJson(bodyJson, Task.class);
                if (tasksManager.getAllTasks().containsKey(task.getIdOfTask())) {
                    tasksManager.updateTask(task);
                    writeResponse(httpExchange, "Обновлено", 201);
                } else {
                    tasksManager.createTask(task.getName(), task.getDescription(), task.getDuration(), task.getLocalDateTime().toString());
                    String response = gson.toJson(tasksManager.getTaskById(task.getIdOfTask()));
                    writeResponse(httpExchange, response, 201);
                }
                break;
            case "epic":
                Epic epic = gson.fromJson(bodyJson, Epic.class);
                if (tasksManager.getAllEpics().containsKey(epic.getIdOfTask())) {
                    tasksManager.updateEpic(epic);
                    writeResponse(httpExchange, "Обновлено", 201);
                } else {
                    tasksManager.createEpic(epic.getName(), epic.getDescription());
                    String response = gson.toJson(tasksManager.getEpicById(epic.getIdOfTask()));
                    writeResponse(httpExchange, response, 201);
                }
                break;
            case "subtask":
                Subtask subtask = gson.fromJson(bodyJson, Subtask.class);
                if (tasksManager.getAllSubTasks().containsKey(subtask.getIdOfTask())) {
                    tasksManager.updateSubtask(subtask);
                    writeResponse(httpExchange, "ПодЗадача_обновлена", 201);
                } else {
                    tasksManager.createSubTask(subtask.getName(), subtask.getDescription(), subtask.getEpicId(), subtask.getDuration(), subtask.getLocalDateTime().toString());
                    writeResponse(httpExchange, "ПодЗадача_создана", 201);
                }
                break;

        }
    }

    private void handleGets(HttpExchange httpExchange) throws IOException {
        String urlToString = httpExchange.getRequestURI().toString();
        String[] splitUrl = urlToString.split("/");
        String idString = httpExchange.getRequestURI().getRawQuery().substring("id=".length());
        int id = parseInt(idString);

        if (splitUrl.length < 3) {
            String response = gson.toJson(tasksManager.getPrioritizedTasks());
            writeResponse(httpExchange, response, 200);
            return;
        }

        switch (splitUrl[SECOND_PARAMETR_IN_REQUESTED_URL_INDEX]) {

            case "task":
                getTask(id, httpExchange);
                break;
            case "epic":
                getEpic(id, httpExchange);
                break;
            case "subtask":
                getSubtask(id, httpExchange);
                break;
            case "history":
                List<Integer> history = tasksManager.getHistory();
                String responseHistory = gson.toJson(history);
                writeResponse(httpExchange, responseHistory, 200);
                break;
        }
    }

    private void writeResponse(HttpExchange httpExchange, String responseString, int responseCode) throws IOException {
        byte[] bytes = responseString.getBytes(UTF_8);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(responseCode, bytes.length);
        httpExchange.getResponseBody().write(bytes);
    }

    private void getTask(int id, HttpExchange httpExchange) throws IOException {
        if (tasksManager.getAllTasks().containsKey(id)) {
            String response = gson.toJson(tasksManager.getAllTasks().get(id));
            writeResponse(httpExchange, response, 200);
        } else {
            String response = "Задачи не существует";
            writeResponse(httpExchange, response, 404);
        }
    }

    private void getEpic(int id, HttpExchange httpExchange) throws IOException {
        if (tasksManager.getAllEpics().containsKey(id)) {
            String response = gson.toJson(tasksManager.getEpic(id));
            writeResponse(httpExchange, response, 200);
        } else {
            String response = "Задачи не существует";
            writeResponse(httpExchange, response, 404);
        }
    }

    private void getSubtask(int id, HttpExchange httpExchange) throws IOException {
        if (tasksManager.getAllSubTasks().containsKey(id)) {
            String response = gson.toJson(tasksManager.getAllSubTasks().get(id));
            writeResponse(httpExchange, response, 200);
        } else {
            String response = "Задачи не существует";
            writeResponse(httpExchange, response, 404);
        }
    }

    private void handleDelete(HttpExchange httpExchange) throws IOException {
        String url = httpExchange.getRequestURI().toString();
        String[] splitURL = url.split("/");

        if (splitURL.length == 2) {
            tasksManager.deleteAll();
            writeResponse(httpExchange, "Успешно удалено", 200);
            return;
        }

        if (httpExchange.getRequestURI().getRawQuery().contains("id=")) {
            String idString = httpExchange.getRequestURI().getRawQuery().substring("id=".length());
            int id = parseInt(idString);
            switch (splitURL[SECOND_PARAMETR_IN_REQUESTED_URL_INDEX]) {
                case "task":
                    tasksManager.deleteTaskById(id);
                    writeResponse(httpExchange, "Удалено " + id, 200);
                    break;
                case "epic":
                    tasksManager.deleteEpicById(id);
                    writeResponse(httpExchange, "Удалено " + id, 200);
                    break;
                case "subtask":
                    tasksManager.deleteSubTaskById(id);
                    writeResponse(httpExchange, "Удалено " + id, 200);
                    break;
            }
        }
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

    class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            try {
                switch (httpExchange.getRequestMethod()) {
                    case "GET":
                        System.out.println("\nОбработка GET-запроса");
                        handleGets(httpExchange);
                        writeResponse(httpExchange, "Успешно", 200);
                        break;
                    case "POST":
                        System.out.println("\nОбработка POST-запроса");
                        handlePosts(httpExchange);
                        writeResponse(httpExchange, "Успешно", 200);
                        break;
                    case "DELETE":
                        System.out.println("\nОбработка DELETE-запроса");
                        handleDelete(httpExchange);
                        writeResponse(httpExchange, "Успешно", 200);
                        break;
                    default:
                        System.out.println("Такого запроса не существует");
                        httpExchange.sendResponseHeaders(405, 0);
                        break;
                }
            } catch (ManagerSaveException | InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                httpExchange.close();
            }
        }
    }
}