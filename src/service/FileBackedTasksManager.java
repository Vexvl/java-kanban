package service;

import model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private static final String FILE_NAME = "history.txt";
    private static final String DIR_TO_SCR = "/src/";
    private static final String DIR_NAME = "resources";
    private static final String BASE_DIRECTORY = "/src/resources/";

    private static final int TYPE_INDEX = 1;
    private static final int TASK_NAME_INDEX = 2;
    private static final int TASK_DESCRIPTION_INDEX = 4;
    private static final int SUBTASK_EPICID_INDEX = 5;
    private static final int TASK_MINUTES_TO_DO_INDEX = 6;
    private static final int TASK_START_TIME_INDEX = 7;

    private File file;

    private List<Integer> history;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    @Override
    public void createTask(String name, String description, int minutesToDo, String startTime) throws ManagerSaveException, IOException {
        super.createTask(name, description, minutesToDo, startTime);
        save();
    }

    @Override
    public void createEpic(String name, String description) throws ManagerSaveException, IOException {
        super.createEpic(name, description);
        save();
    }

    @Override
    public void createSubTask(String name, String description, int epicId, int minutesToDo, String startTime) throws ManagerSaveException, IOException {
        super.createSubTask(name, description, epicId, minutesToDo, startTime);
        save();
    }

    public void save() throws ManagerSaveException, IOException {

        try {

            if (!Files.exists(Paths.get(DIR_TO_SCR, DIR_NAME))) {
                Files.createDirectory(Paths.get(DIR_TO_SCR, DIR_NAME));
            }
            if (!Files.exists(Paths.get(BASE_DIRECTORY, FILE_NAME))) {
                Files.createFile(Paths.get(BASE_DIRECTORY, FILE_NAME));
            }

            FileWriter fileWriter = new FileWriter(FILE_NAME);
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader br = new BufferedReader(fileReader);
            if (br.readLine() == null) {
                fileWriter.write("id,type,name,status,description,epic,startTime,minutesToDo,endTime" + "\n");
            }
            for (Object task : getAllTypeTasks().values()) {
                fileWriter.write(task.toString() + "\n");
            }
            fileWriter.write("\n");
            fileWriter.write(historyToString((getHistoryManager())));
            fileWriter.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка во время сохранения");
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


    public static void main(String[] args) throws ManagerSaveException, ManagerReadException, IOException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("history.txt"));
        fileBackedTasksManager.createTask("Задача№1", "ОписаниеЗадача№1", 30, "2021-12-21T21:21:21");
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getTask(1));
        fileBackedTasksManager.createTask("Задача№2", "ОписаниеЗадача№2", 67, "2025-12-21T21:21:21");
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getTask(2));
        fileBackedTasksManager.createEpic("Эпик1", "ОписаниеЭпик1");
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getEpic(3));
        fileBackedTasksManager.createSubTask("Подзадача1", "ОписаниеПодзадача2", 3, 36, "2056-12-21T21:21:21");
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getSubtask(4));
        fileBackedTasksManager.createSubTask("Подзадача2", "ОписаниеПодзадача2", 3, 90, "2042-12-21T21:21:21");
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getSubtask(5));
        fileBackedTasksManager.createSubTask("Подзадача3", "ОписаниеПодзадача3", 3, 99, "2078-12-21T21:21:21");
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getSubtask(6));
        FileBackedTasksManager.getHistoryManager().add(fileBackedTasksManager.getEpic(7));
        fileBackedTasksManager.save();
        FileBackedTasksManager fileBackedTasksManager1 = loadFromFile(new File(FILE_NAME));
        System.out.println("test");
    }
}