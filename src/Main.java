import service.HistoryManager;
import service.Managers;
import service.TaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        historyManager.add(taskManager.createTask("Задача№1", "ОписаниеЗадача№1"));
        historyManager.add(taskManager.createTask("Задача№2", "ОписаниеЗадача№2"));
        historyManager.add(taskManager.createEpic("Эпик1","ОписаниеЭпик1"));
        historyManager.add(taskManager.createSubTask("Подзадача1","ОписаниеПодзадача2",3));
        historyManager.add(taskManager.createSubTask("Подзадача2","ОписаниеПодзадача2",3));
        historyManager.add(taskManager.createSubTask("Подзадача3","ОписаниеПодзадача2",3));
        historyManager.add(taskManager.createEpic("Эпик2", "ОписаниеЭпик2"));

        System.out.println("test");
    }
}