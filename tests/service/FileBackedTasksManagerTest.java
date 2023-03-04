package service;

import model.ManagerReadException;
import model.ManagerSaveException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static service.FileBackedTasksManager.historyToString;
import static service.FileBackedTasksManager.loadFromFile;
import static service.InMemoryTaskManager.getHistoryManager;

class FileBackedTasksManagerTest extends TaskManagerTest {

    private static final String BASE_DIRECTORY = "/src/resources/";
    private static final String DIR_TO_SRC = "/src/";
    private static final String DIR_NAME = "resources";
    private static final String FILE_NAME = "history.txt";
    private static final String PATH = "src\\resources\\history.txt";

    private TaskManager fileManager = new FileBackedTasksManager(new File(PATH));
    private TaskManager taskManager = new InMemoryTaskManager();

    @Test
    public void createTask() throws ManagerSaveException, IOException, ManagerReadException {
        fileManager.createTask("Задача№1", "ОписаниеЗадача№1", 30, "2021-12-21T21:21:21");
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 30, "2021-12-21T21:21:21");
        assertEquals(fileManager.getTaskById(1), taskManager.getTask(1));
    }

    @Test
    public void createEpic() throws ManagerSaveException, IOException, ManagerReadException {
        fileManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        assertEquals(taskManager.getEpic(1), fileManager.getEpic(1));
    }

    @Test
    public void createSubtask() throws ManagerSaveException, IOException, ManagerReadException {
        fileManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        fileManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 3, "2056-12-21T21:21:21");
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 3, "2056-12-21T21:21:21");
        assertEquals(taskManager.getSubTaskById(1), fileManager.getSubtask(1));
    }

    private void save() throws ManagerSaveException, IOException {

        try {

            if (!Files.exists(Paths.get(DIR_TO_SRC, DIR_NAME))) {
                Files.createDirectory(Paths.get(DIR_TO_SRC, DIR_NAME));
            }

            if (!Files.exists(Paths.get(BASE_DIRECTORY, FILE_NAME))) {
                Files.createFile(Paths.get(BASE_DIRECTORY, FILE_NAME));
            }

            FileWriter fileWriter = new FileWriter(FILE_NAME);
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader br = new BufferedReader(fileReader);
            if (br.readLine() == null) {
                fileWriter.write("id,type,name,status,description,epic, minutesToDo, startTime" + "\n");
            }
            for (Object task : taskManager.getAllTypeTasks().values()) {
                fileWriter.write(task.toString() + "\n");
            }
            fileWriter.write("\n");
            fileWriter.write(historyToString((getHistoryManager())));
            fileWriter.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка во время сохрания");
        }
    }

    @Test
    public void testEmptyTasks() throws ManagerReadException, ManagerSaveException, IOException {
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File(PATH));
        fileBackedTasksManager.createTask("Задача№1", "ОписаниеЗадача№1", 30, "2021-12-21T21:21:21");
        fileBackedTasksManager.deleteAll();
        assertEquals(0, fileBackedTasksManager.getAllTasks().size());
    }

    @Test
    public void testEpicWithoutSubtasks() throws ManagerReadException, ManagerSaveException, IOException {
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File(PATH));
        fileBackedTasksManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        fileBackedTasksManager.getEpic(1).getSubtasks().clear();
        assertEquals(0, fileBackedTasksManager.getEpic(1).getSubtasks().size());
    }

    @Test
    public void emptyHistory() throws ManagerReadException, ManagerSaveException, IOException {
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File(PATH));
        fileBackedTasksManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        fileBackedTasksManager.getHistory().clear();
        assertEquals(0, fileBackedTasksManager.getHistory().size());
    }
}