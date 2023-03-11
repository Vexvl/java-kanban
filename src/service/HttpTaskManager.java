package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import model.Epic;
import model.Subtask;
import model.Task;
import server.KVTaskClient;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
    private final KVTaskClient client;


    public HttpTaskManager(String URL) throws IOException, InterruptedException {
        super(new File(URL));
        client = new KVTaskClient(URL);
    }

    public static HttpTaskManager load(String url) throws IOException, InterruptedException {
        HttpTaskManager taskManager = new HttpTaskManager(url);
        KVTaskClient client = taskManager.getKVTaskClient();
        String jsonTasks = client.load("tasks");
        String jsonEpics = client.load("epics");
        String jsonSubtasks = client.load("subtasks");
        String jsonHistory = client.load("history");

        Type mapType = new TypeToken<HashMap<Integer, Task>>() {
        }.getType();
        HashMap<Integer, Task> tasksRecover = gson.fromJson(jsonTasks, mapType);
        for (Integer key : tasksRecover.keySet()) {
            taskManager.tasks.put(key, tasksRecover.get(key));
        }

        mapType = new TypeToken<HashMap<Integer, Epic>>() {
        }.getType();
        HashMap<Integer, Epic> epicsRecover = gson.fromJson(jsonEpics, mapType);
        for (Integer key : epicsRecover.keySet()) {
            taskManager.epics.put(key, epicsRecover.get(key));
        }

        mapType = new TypeToken<HashMap<Integer, Subtask>>() {
        }.getType();
        HashMap<Integer, Subtask> subtasksRecover = gson.fromJson(jsonSubtasks, mapType);
        for (Integer key : subtasksRecover.keySet()) {
            taskManager.subTasks.put(key, subtasksRecover.get(key));
        }

        Type listType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> historyRecover = gson.fromJson(jsonHistory, listType);
        HashMap<Integer, Task> allTasks = new HashMap<>(taskManager.tasks);
        allTasks.putAll(taskManager.epics);
        allTasks.putAll(taskManager.subTasks);
        historyRecover.forEach(task -> historyManager.add(allTasks.get(task.getIdOfTask())));

        return taskManager;
    }

    public KVTaskClient getKVTaskClient() {
        return client;
    }

    @Override
    public void save() throws IOException, InterruptedException {
        String jsonTasks = gson.toJson(tasks);
        String jsonEpics = gson.toJson(epics);
        String jsonSubtasks = gson.toJson(subTasks);
        String jsonHistory = gson.toJson(historyManager.getHistory());

        client.put("tasks", jsonTasks);
        client.put("epics", jsonEpics);
        client.put("subtasks", jsonSubtasks);
        client.put("history", jsonHistory);
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