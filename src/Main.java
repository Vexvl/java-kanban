import model.ManagerSaveException;
import service.HistoryManager;
import service.Managers;
import service.TaskManager;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws ManagerSaveException, IOException {

        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        taskManager.createTask("Задача№1", "ОписаниеЗадача№1");
        historyManager.add(taskManager.getTask(1));
        taskManager.createTask("Задача№2", "ОписаниеЗадача№2");
        historyManager.add(taskManager.getTask(2));
        taskManager.createEpic("Эпик1", "ОписаниеЭпик1");
        historyManager.add(taskManager.getEpic(3));
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача2", 3);
        historyManager.add(taskManager.getSubTaskById(4));
        taskManager.createSubTask("Подзадача2", "ОписаниеПодзадача2", 3);
        historyManager.add(taskManager.getSubTaskById(5));
        taskManager.createSubTask("Подзадача3", "ОписаниеПодзадача3", 3);
        historyManager.add(taskManager.getSubTaskById(6));
        taskManager.createEpic("Эпик2", "ОписаниеЭпик2");
        historyManager.add(taskManager.getEpic(7));
        historyManager.remove(historyManager.getNodeById(3), taskManager.getEpic(3));
        System.out.println(historyManager.getHistory());


    }
}