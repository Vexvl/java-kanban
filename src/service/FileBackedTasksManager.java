package service;

import exception.ManagerReadException;
import exception.ManagerSaveException;
import model.Epic;
import model.Subtask;
import model.Task;
import model.Type;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private static final String FILE_NAME = "history.txt";
    private static final String BASE_DIRECTORY = "src/resources";

    private static final int TYPE_INDEX = 1;
    private static final int TASK_NAME_INDEX = 2;
    private static final int TASK_DESCRIPTION_INDEX = 4;
    private static final int SUBTASK_EPICID_INDEX = 5;
    private static final int TASK_START_TIME_INDEX = 6;
    private static final int TASK_MINUTES_TO_DO_INDEX = 7;

    private File file;

    private List<Integer> history;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) throws ManagerSaveException, ManagerReadException, IOException, InterruptedException {
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
        fileBackedTasksManager.save();
        FileBackedTasksManager fileBackedTasksManager1 = loadFromFile(fileBackedTasksManager.getFile());
        System.out.println("test");
    }

    static FileBackedTasksManager loadFromFile(File file) throws ManagerReadException, ManagerSaveException, IOException, InterruptedException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.fromString(fileBackedTasksManager.readFile(file));
        return fileBackedTasksManager;
    }

    @Override
    public void createTask(String name, String description, int duration, String startTime) throws ManagerSaveException, IOException, InterruptedException {
        super.createTask(name, description, duration, startTime);
        save();
    }

    @Override
    public void createEpic(String name, String description) throws ManagerSaveException, IOException, InterruptedException {
        super.createEpic(name, description);
        save();
    }

    @Override
    public void createSubTask(String name, String description, int epicId, int duration, String startTime) throws ManagerSaveException, IOException, InterruptedException {
        super.createSubTask(name, description, epicId, duration, startTime);
        save();
    }

    @Override
    public void updateTask(Task task) throws ManagerSaveException, IOException, InterruptedException {
        super.updateTask(task);
        save();
    }

    static String historyToString(HistoryManager manager) {
        int commasToWrite = (manager.getHistoryHash().size() - 1);
        int commasWritten = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer id : manager.getHistoryHash().keySet()) {
            stringBuilder.append(id);
            if (commasWritten < commasToWrite) {
                stringBuilder.append(",");
                commasWritten++;
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void updateEpic(Epic epic) throws ManagerSaveException, IOException, InterruptedException {
        super.updateTask(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) throws ManagerSaveException, IOException, InterruptedException {
        super.updateTask(subtask);
        save();
    }

    public List<Integer> getHistory() {
        return history;
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

    public File getFile() {
        return file;
    }

    String readFile(File file) throws ManagerReadException {
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            throw new ManagerReadException("Прочитать файл не удалось");
        }
    }

    public void save() throws ManagerSaveException, IOException, InterruptedException {

        try {

            File dir = new File(BASE_DIRECTORY);
            dir.mkdir();
            File historyFile = new File(dir, FILE_NAME);

            file = historyFile;

            FileWriter fileWriter = new FileWriter(historyFile);
            FileReader fileReader = new FileReader(historyFile);
            BufferedReader br = new BufferedReader(fileReader);

            if (br.readLine() == null) {
                fileWriter.write("id,type,name,status,description,epic,startTime,duration,endTime" + "\n");
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

    public Task fromString(String value) throws ManagerSaveException, IOException, InterruptedException {

        String[] columns = value.split("\n");
        int columnsLength = columns.length;
        for (int i = 1; i < (columnsLength - 2); i++) {
            String[] taskFields = columns[i].split(",");
            Type taskType = Type.valueOf((taskFields[TYPE_INDEX]));
            String taskName = taskFields[TASK_NAME_INDEX];
            String taskDescription = taskFields[TASK_DESCRIPTION_INDEX];
            int taskMinutesToDo;
            String startTime;
            switch (taskType) {
                case TASK:
                    startTime = taskFields[TASK_START_TIME_INDEX - 1];
                    taskMinutesToDo = Integer.parseInt(taskFields[TASK_MINUTES_TO_DO_INDEX - 1]);
                    createTask(taskName, taskDescription, taskMinutesToDo, startTime);
                    break;
                case EPIC:
                    createEpic(taskName, taskDescription);
                    break;
                case SUBTASK:
                    int epicId = Integer.parseInt(taskFields[SUBTASK_EPICID_INDEX]);
                    taskMinutesToDo = Integer.parseInt(taskFields[TASK_MINUTES_TO_DO_INDEX]);
                    startTime = taskFields[TASK_START_TIME_INDEX];
                    createSubTask(taskName, taskDescription, epicId, taskMinutesToDo, startTime);
                    break;
            }
        }
        history = historyFromString(columns[columnsLength - 1]);
        return null;
    }
}