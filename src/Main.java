import service.HistoryManager;
import service.Managers;
import service.TaskManager;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        historyManager.addTask(taskManager.createTask("Задача№1", "ОписаниеЗадача№1"));
        historyManager.addTask(taskManager.createEpic("Эпик1","ОписаниеЭпик1"));
        historyManager.addTask(taskManager.createSubTask("Подзадача1","ОписаниеПодзадача2",2));

        System.out.println("test");
    }
}