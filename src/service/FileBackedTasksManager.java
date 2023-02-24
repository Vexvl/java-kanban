package service;

import model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    @Override
    public void createTask(String name, String description) throws ManagerSaveException {
        super.createTask(name, description);
        save();
    }

    @Override
    public void createEpic(String name, String description) throws ManagerSaveException {
        super.createEpic(name, description);
        save();
    }

    @Override
    public void createSubTask(String name, String description, int epicId) throws ManagerSaveException {
        super.createSubTask(name, description, epicId);
        save();
    }

    public void save() throws ManagerSaveException {

        try {
            String fileName = "history.txt";
            String dir = "C:\\Users\\User\\dev\\sprints\\thirdsprint\\java-kanban\\src";

            if (!Files.exists(Paths.get(dir, "resources"))) {
                Files.createDirectory(Paths.get(dir, "resources"));
            }

            if (!Files.exists(Paths.get(dir + "\\resources", fileName))) {
                Files.createFile(Paths.get(dir + "\\resources", fileName));
            }

            FileWriter fileWriter = new FileWriter(dir + "\\resources\\history.txt");
            FileReader fileReader = new FileReader(dir + "\\resources\\history.txt");
            BufferedReader br = new BufferedReader(fileReader);
            if (br.readLine() == null) {
                fileWriter.write("id,type,name,status,description,epic" + "\n");
            }
            for (Object task : getEverything().values()){
                fileWriter.write(task.toString() + "\n");
            }
            fileWriter.write("\n");
          //  fileWriter.write(historyToString((getHistoryManager())));
            fileWriter.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка во время сохрания");
        }
    }



    public Task fromString(String value) throws ManagerSaveException {
            String[] split = value.split(",");
            Type type = Type.valueOf(split[1]);
            String name = split[2];
            String description = split[4];
            switch (type) {
                case TASK:
                    createTask(name,description);
                    break;
                case EPIC:
                    createEpic(name, description);
                    break;
                case SUBTASK:
                    int epicId = Integer.parseInt(split[5]);
                    createSubTask(name, description, epicId);
                    break;
                default:
                    break;
            }
            return null;
}
    static List<Integer> historyFromString(String value){
        try {
            List <Integer> historyTasksId = new ArrayList<>();
            String[] split = value.split(",");
            for (String id : split){
                historyTasksId.add(Integer.parseInt(id));
            }
            return historyTasksId;
        }
        catch (NumberFormatException e){
            System.out.println("Ошибка записи в историю");
        }
        return null;
    }

    static String historyToString(HistoryManager manager) {
        StringBuilder stringBuilder = new StringBuilder();
        for (model.Task task : manager.getHistory()){
            stringBuilder.append(task.getIdOfTask());
        }
        return String.join(",", stringBuilder.toString());
    }

    private String readFile(File file) throws ManagerReadException {
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            throw new ManagerReadException("Прочитать файл не удалось");
        }
    }

    static FileBackedTasksManager loadFromFile(File file) throws ManagerReadException, ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        List<String> lines = List.of(fileBackedTasksManager.readFile(file).split("\n"));
        for (int i = 1; i < lines.size(); i++){
           if ((lines.size() - 1 > 1) && !lines.get(i).isBlank() ) {
                fileBackedTasksManager.fromString(lines.get(i));
            }
           else historyFromString(lines.get(i));
        }
        return fileBackedTasksManager;
    }




    public static void main(String[] args) throws ManagerSaveException, ManagerReadException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("history.txt"));
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
        loadFromFile(new File("history.txt"));
        System.out.println("test");
    }
}
