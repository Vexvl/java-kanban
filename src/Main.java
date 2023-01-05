import managers.HistoryManager;
import managers.Managers;
import managers.TaskManager;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();
        HistoryManager memoryManager = Managers.getDefaultHistory();

        memoryManager.addTask(taskManager.createTask("Задача№1", "ОписаниеЗадача№1"));
        memoryManager.addTask(taskManager.createEpic("Эпик1","ОписаниеЭпик1"));
        memoryManager.addTask(taskManager.createSubTask("Подзадача1","ОписаниеПодзадача2",2));
        taskManager.changeStatusSubtasktoDone(3);
        taskManager.updateStatusOfEpic(2);
        System.out.println("test");
    }
}