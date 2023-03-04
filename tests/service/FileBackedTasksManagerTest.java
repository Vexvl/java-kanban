package service;

import model.ManagerReadException;
import model.ManagerSaveException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static service.FileBackedTasksManager.historyToString;
import static service.FileBackedTasksManager.loadFromFile;
import static service.InMemoryTaskManager.getHistoryManager;

class FileBackedTasksManagerTest extends TaskManagerTest {

    private static final String BASE_DIRECTORY = "/src/resources/";
    private static final String DIR_TO_SRC = "/src/";
    private static final String DIR_NAME = "resources";
    private static final String FILE_NAME = "history.txt";

    private TaskManager taskManager = new FileBackedTasksManager(new File(FILE_NAME));

    private List<Integer> history;

    @Test
    public void createTask() throws ManagerSaveException, IOException, ManagerReadException {
        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 22, "2026-12-21T21:21:21");
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File(FILE_NAME));
        assertEquals(taskManager.getTaskById(1), fileBackedTasksManager.getTask(1));
    }

    @Test
    public void createEpic() throws ManagerSaveException, IOException, ManagerReadException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File(FILE_NAME));
        assertEquals(taskManager.getEpicById(1), fileBackedTasksManager.getEpic(1));
    }

    @Test
    public void createSubtask() throws ManagerSaveException, IOException, ManagerReadException {
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 22, "2026-12-21T21:21:21");
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File(FILE_NAME));
        assertEquals(taskManager.getSubTaskById(1), fileBackedTasksManager.getSubtask(1));
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
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File(FILE_NAME));
        assertNull(fileBackedTasksManager.getAllTasks());
    }

    @Test
    public void testEpicWithoutSubtasks() throws ManagerReadException, ManagerSaveException, IOException {
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File(FILE_NAME));
        assertNull(fileBackedTasksManager.getEpic(1).getSubtasks());
    }

    @Test
    public void emptyHistory() throws ManagerReadException, ManagerSaveException, IOException {
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File(FILE_NAME));
        assertNull(history.isEmpty());
    }

}