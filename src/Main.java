import model.IntersectionException;
import model.ManagerSaveException;
import model.Task;
import service.HistoryManager;
import service.Managers;
import service.TaskManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;


public class Main {

    public static void main(String[] args) throws ManagerSaveException, IOException, IntersectionException {

        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        taskManager.createTask("Задача№1", "ОписаниеЗадача№1", 30, "2021-12-21T21:21:21");
        historyManager.add(taskManager.getTask(1));
        taskManager.createTask("Задача№2", "ОписаниеЗадача№2", 67, "2025-12-21T21:21:21");
      //  taskManager.IfIntersection(taskManager.getTask(2));
        historyManager.add(taskManager.getTask(2));
        taskManager.createEpic("Эпик1", "ОписаниеЭпик1");
      //  taskManager.IfIntersection(taskManager.getTask(3));
        historyManager.add(taskManager.getEpic(3));
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача2", 3, 36, "2056-12-21T21:21:21");
     //   taskManager.IfIntersection(taskManager.getTask(4));
        historyManager.add(taskManager.getSubTaskById(4));
        taskManager.createSubTask("Подзадача2", "ОписаниеПодзадача2", 3, 90, "2042-12-21T21:21:21");
     //   taskManager.IfIntersection(taskManager.getTask(5));
        historyManager.add(taskManager.getSubTaskById(5));
        taskManager.createSubTask("Подзадача3", "ОписаниеПодзадача3", 3, 99, "2078-12-21T21:21:21");
       // taskManager.IfIntersection(taskManager.getTask(6));
        historyManager.add(taskManager.getSubTaskById(6));
        taskManager.createEpic("Эпик2", "ОписаниеЭпик2");
      //  taskManager.IfIntersection(taskManager.getTask(7));
        historyManager.add(taskManager.getEpic(7));
        historyManager.remove(historyManager.getNodeById(3), taskManager.getEpic(3));
       TreeSet<Task> getPrioritizedTasks = taskManager.getPrioritizedTasks();
        System.out.println(historyManager.getHistory());


    }
}