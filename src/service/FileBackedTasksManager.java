package service;

import model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private File file;

    private List<Integer> history;

    private static final String BASE_DIRECTORY = "/src/resources/";

    private static final String DIR_TO_SRC = "/src/";

    private static final String DIR_NAME = "resources";

    private static final String FILE_NAME = "history.txt";

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    @Override
    public void createTask(String name, String description) throws ManagerSaveException, IOException {
        super.createTask(name, description);
        save();
    }

    @Override
    public void createEpic(String name, String description) throws ManagerSaveException, IOException {
        super.createEpic(name, description);
        save();
    }

    @Override
    public void createSubTask(String name, String description, int epicId) throws ManagerSaveException, IOException {
        super.createSubTask(name, description, epicId);
        save();
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
                fileWriter.write("id,type,name,status,description,epic" + "\n");
            }
            for (Object task : getAllTypeTasks().values()) {
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
            Type taskType = Type.valueOf((taskFields[1]));
            String taskName = taskFields[2];
            String taskDescription = taskFields[4];
            switch (taskType) {
                case TASK:
                    createTask(taskName, taskDescription);
                    break;
                case EPIC:
                    createEpic(taskName, taskDescription);
                    break;
                case SUBTASK:
                    int epicId = Integer.parseInt(taskFields[5]);
                    createSubTask(taskName, taskDescription, epicId);
                    break;
            }
        }
        history = historyFromString(columns[columnsLength - 1]);
        return null;
    }

    static List<Integer> historyFromString(String historyTextLine) {
        try {
            List<Integer> historyTasksId = new ArrayList<>();
            String[] taskIds = historyTextLine.split(",");
            for (String id : taskIds) {
                historyTasksId.add(Integer.parseInt(id));
            }
            return historyTasksId;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка записи в историю");
        }
        return null;
    }

    static String historyToString(HistoryManager manager) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer id : manager.getHistoryHash().keySet()) {
            stringBuilder.append(id + ",");
        }
        String ids = String.join(",", stringBuilder.toString());
        return ids;
    }

    private String readFile(File file) throws ManagerReadException {
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


    public static void main(String[] args) throws ManagerSaveException, ManagerReadException, IOException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("history.txt"));
        HistoryManager historyManager = fileBackedTasksManager.getHistoryManager();
        fileBackedTasksManager.createTask("Задача№1", "ОписаниеЗадача№1");
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getTask(1));
        fileBackedTasksManager.createTask("Задача№2", "ОписаниеЗадача№2");
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getTask(2));
        fileBackedTasksManager.createEpic("Эпик1", "ОписаниеЭпик1");
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getEpic(3));
        fileBackedTasksManager.createSubTask("Подзадача1", "ОписаниеПодзадача2", 3);
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getSubtask(4));
        fileBackedTasksManager.createSubTask("Подзадача2", "ОписаниеПодзадача2", 3);
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getSubtask(5));
        fileBackedTasksManager.createSubTask("Подзадача3", "ОписаниеПодзадача3", 3);
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getSubtask(6));
        fileBackedTasksManager.createEpic("Эпик2", "ОписаниеЭпик2");
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getEpic(7));
        fileBackedTasksManager.save();
        FileBackedTasksManager fileBackedTasksManager1 = loadFromFile(new File(FILE_NAME));
        System.out.println("test");
    }
}