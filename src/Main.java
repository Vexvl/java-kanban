import model.Epic;
import model.Node;
import model.Task;
import service.HistoryManager;
import service.Managers;
import service.TaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        historyManager.addTask(taskManager.createTask("Задача№1", "ОписаниеЗадача№1"));
        historyManager.addTask(taskManager.createTask("Задача№2", "ОписаниеЗадача№2"));
        historyManager.addTask(taskManager.createEpic("Эпик1","ОписаниеЭпик1"));
        historyManager.addTask(taskManager.createSubTask("Подзадача1","ОписаниеПодзадача2",3));
        historyManager.addTask(taskManager.createSubTask("Подзадача1","ОписаниеПодзадача2",3));
        historyManager.addTask(taskManager.createSubTask("Подзадача2","ОписаниеПодзадача2",3));
        historyManager.addTask(taskManager.createSubTask("Подзадача3","ОписаниеПодзадача2",3));
        historyManager.remove(3);
        historyManager.removeNodeEpic(new Node<>(taskManager.getEpicById(3)));
        historyManager.getHistory();


        System.out.println("test");
    }
}