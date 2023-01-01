import manager.Manager;
import tasks.Epic;

public class Main {
    public static void main(String[] args) {

        Manager manager = new Manager();

        manager.createTask("Задача №1", "ОписаниеЗадача№1");
        manager.createTask("Задача №2", "ОписаниеЗадача№2");
        manager.createEpic("Эпик №1", "ОписаниеЭпик №1");
        manager.createSubTask("Подзадача №1", "ОписаниеПодзадача №1", 3);
        manager.createSubTask("Подзадача №2", "ОписаниеПодзадача №2", 3);
        manager.createEpic("Эпик №2", "ОписаниеЭпик №2");
        manager.createSubTask("Подзадача №3", "ОписаниеПодзадача №3", 6);
        System.out.println(manager.getEpics());
        System.out.println(manager.getTasks());
        System.out.println(manager.getSubtasks());
        manager.updateStatusOfEpic((Epic) manager.getEpics().get(3));
        System.out.println(manager.getEpics());
        manager.deleteSubTaskById(7);
        manager.changeStatusSubtasktoDone(5);
        System.out.println(manager.getEpics());
        System.out.println(manager.getTasks());
        System.out.println(manager.getSubtasks());
    }
}