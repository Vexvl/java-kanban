package service;

import model.ManagerReadException;
import model.ManagerSaveException;
import model.Task;
import model.Type;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static service.InMemoryTaskManager.getHistoryManager;

class FileBackedTasksManagerTest {

    private static final String BASE_DIRECTORY = "/src/resources/";
    private static final String DIR_TO_SRC = "/src/";
    private static final String DIR_NAME = "resources";
    private static final String FILE_NAME = "history.txt";

    private static final int TYPE_INDEX = 1;
    private static final int TASK_NAME_INDEX = 2;
    private static final int TASK_DESCRIPTION_INDEX = 4;
    private static final int SUBTASK_EPICID_INDEX = 5;
    private static final int TASK_MINUTES_TO_DO_INDEX = 6;
    private static final int TASK_START_TIME_INDEX = 7;

    private TaskManager taskManager = new InMemoryTaskManager();

    private List<Integer> history;

    public void createTask(String name, String description, int minutesToDo, String startTime) throws ManagerSaveException, IOException {
        taskManager.createTask(name, description, minutesToDo, startTime);
        save();
    }

    public void createEpic(String name, String description) throws ManagerSaveException, IOException {
        taskManager.createEpic(name, description);
        save();
    }

    public void createSubTask(String name, String description, int epicId, int minutesToDo, String startTime) throws ManagerSaveException, IOException {
        taskManager.createSubTask(name, description, epicId, minutesToDo, startTime);
        save();
    }

    public List<Integer> getHistory() {
        return history;
    }


    public void save() throws ManagerSaveException, IOException {

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


    public Task fromString(String value) throws ManagerSaveException, IOException {

        String[] columns = value.split("\n");
        int columnsLength = columns.length;
        for (int i = 1; i < (columnsLength - 2); i++) {
            String[] taskFields = columns[i].split(",");
            Type taskType = Type.valueOf((taskFields[TYPE_INDEX]));
            String taskName = taskFields[TASK_NAME_INDEX];
            String taskDescription = taskFields[TASK_DESCRIPTION_INDEX];
            int taskMinutesToDo = Integer.parseInt(taskFields[TASK_MINUTES_TO_DO_INDEX]);
            String startTime = taskFields[TASK_START_TIME_INDEX];
            switch (taskType) {
                case TASK:
                    createTask(taskName, taskDescription, taskMinutesToDo, startTime);
                    break;
                case EPIC:
                    createEpic(taskName, taskDescription);
                    break;
                case SUBTASK:
                    int epicId = Integer.parseInt(taskFields[SUBTASK_EPICID_INDEX]);
                    createSubTask(taskName, taskDescription, epicId, taskMinutesToDo, startTime);
                    break;
            }
        }
        history = historyFromString(columns[columnsLength - 1]);
        return null;
    }

    static List<Integer> historyFromString(String historyTextLine) {
        List<Integer> historyTasksId = new ArrayList<>();
        try {
            String[] taskIds = historyTextLine.split(",");
            for (String id : taskIds) {
                historyTasksId.add(Integer.parseInt(id));
            }
            return historyTasksId;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка записи в историю");
        }
        return new ArrayList<>(historyTasksId);
    }

    static String historyToString(HistoryManager manager) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer id : manager.getHistoryHash().keySet()) {
            stringBuilder.append(id + ",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length());
        String ids = String.join(",", stringBuilder.toString());
        return ids;
    }

    String readFile(File file) throws ManagerReadException {
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            throw new ManagerReadException("Прочитать файл не удалось");
        }
    }

    static FileBackedTasksManager loadFromFile(File file) throws ManagerReadException, ManagerSaveException, IOException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.fromString(fileBackedTasksManager.readFile(file));
        return fileBackedTasksManager;
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
        assertNull(getHistory().isEmpty());
    }

}