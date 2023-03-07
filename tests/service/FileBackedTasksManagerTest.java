package service;

import exceptions.ManagerReadException;
import exceptions.ManagerSaveException;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static service.FileBackedTasksManager.historyToString;
import static service.FileBackedTasksManager.loadFromFile;
import static service.InMemoryTaskManager.historyManager;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private static final String FILE_NAME = "historyTest.txt";
    private static final String BASE_DIRECTORY = "tests/resources";
    private static final String PATH = "tests/resources/historyTest.txt";

    @BeforeEach
    public void setTaskManager() {
        this.taskManager = new FileBackedTasksManager(new File(PATH));
    }

    private void saveFile(List<Task> tasks) throws IOException {
        File dir = new File(BASE_DIRECTORY);
        dir.mkdir();

        File historyFile = new File(dir, FILE_NAME);

        FileWriter fileWriter = new FileWriter(historyFile);
        FileReader fileReader = new FileReader(historyFile);
        BufferedReader br = new BufferedReader(fileReader);

        if (br.readLine() == null) {
            fileWriter.write("id,type,name,status,description,epic,startTime,minutesToDo,endTime" + "\n");
        }

        for (Task task : tasks) {
            fileWriter.write(task.toString() + "\n");
        }

        fileWriter.write("\n");
        fileWriter.write(historyToString((historyManager)));
        fileWriter.close();
    }

    @Test
    public void createTask() throws ManagerSaveException, IOException, ManagerReadException {
        List<Task> tasks = new ArrayList<>();
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 30, "2021-12-21T21:21:21");
        tasks.add(taskManager.getTask(1));
        historyManager.add(taskManager.getTask(1));
        saveFile(tasks);
        TaskManager fileBackedTasksManager = loadFromFile(new File(PATH));
        assertEquals(fileBackedTasksManager.getTaskById(1), taskManager.getTask(1));
    }

    @Test
    public void createEpic() throws ManagerSaveException, IOException, ManagerReadException {
        List<Task> tasks = new ArrayList<>();
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        tasks.add(taskManager.getEpic(1));
        historyManager.add(taskManager.getEpic(1));
        saveFile(tasks);
        TaskManager fileBackedTasksManager = loadFromFile(new File(PATH));
        assertEquals(taskManager.getEpic(1), fileBackedTasksManager.getEpic(1));
    }

    @Test
    public void createSubtask() throws ManagerSaveException, IOException, ManagerReadException {
        List<Task> tasks = new ArrayList<>();
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 3, "2056-12-21T21:21:21");
        tasks.add(taskManager.getEpic(1));
        historyManager.add(taskManager.getEpic(1));
        saveFile(tasks);
        TaskManager fileBackedTasksManager = loadFromFile(new File(PATH));
        assertEquals(taskManager.getSubTaskById(1), fileBackedTasksManager.getSubtask(1));
    }


    @Test
    public void save() throws IOException, ManagerReadException, ManagerSaveException {
        List<Task> tasks = new ArrayList<>();
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 1, "2029-12-21T21:21:21");
        taskManager.createTask("Задача№2", "ОписаниеЗадача№1", 2, "2029-12-21T21:21:21");
        taskManager.createTask("Задача№3", "ОписаниеЗадача№1", 3, "2029-12-21T21:21:21");
        taskManager.createTask("Задача№4", "ОписаниеЗадача№1", 3, "2029-12-21T21:21:21");
        tasks.add(taskManager.getTask(1));
        tasks.add(taskManager.getTask(2));
        tasks.add(taskManager.getTask(3));
        tasks.add(taskManager.getTask(4));
        historyManager.add(taskManager.getTask(1));
        historyManager.add(taskManager.getTask(2));
        historyManager.add(taskManager.getTask(3));
        historyManager.add(taskManager.getTask(4));
        saveFile(tasks);
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File(PATH));
        assertEquals(4, fileBackedTasksManager.getHistory().size());
    }

    @Test
    public void testEmptyTasks() throws ManagerReadException, ManagerSaveException, IOException {
        List<Task> tasks = new ArrayList<>();
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 30, "2021-12-21T21:21:21");
        tasks.add(taskManager.getTask(1));
        historyManager.add(taskManager.getTask(1));
        saveFile(tasks);
        TaskManager fileBackedTasksManager = loadFromFile(new File(PATH));
        fileBackedTasksManager.deleteAll();
        assertEquals(0, fileBackedTasksManager.getAllTasks().size());
    }

    @Test
    public void testEpicWithoutSubtasks() throws ManagerReadException, ManagerSaveException, IOException {
        List<Task> tasks = new ArrayList<>();
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        tasks.add(taskManager.getEpic(1));
        historyManager.add(taskManager.getEpic(1));
        saveFile(tasks);
        TaskManager fileBackedTasksManager = loadFromFile(new File(PATH));
        taskManager.getEpic(1).getSubtasks().clear();
        assertEquals(0, fileBackedTasksManager.getEpic(1).getSubtasks().size());
    }

    @Test
    public void emptyHistory() throws ManagerReadException, ManagerSaveException, IOException {
        List<Task> tasks = new ArrayList<>();
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        tasks.add(taskManager.getEpic(1));
        historyManager.add(taskManager.getEpic(1));
        saveFile(tasks);
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File(PATH));
        taskManager.getEpic(1).getSubtasks().clear();
        fileBackedTasksManager.getHistory().clear();
        assertEquals(0, fileBackedTasksManager.getHistory().size());
    }
}