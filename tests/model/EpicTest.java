package model;

import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;
import service.TaskManager;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicTest {

    private TaskManager taskManager = new InMemoryTaskManager();
    @Test
    public void ShallReturnNoSubtasks() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        assertEquals(0,taskManager.getEpicById(1).getSubtasks().size());
    }

    @Test
    public void StatusInProgressOfEpicWhenEverySubtaskIsNew() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 3, "2029-12-21T21:21:21");
        taskManager.createSubTask("Подзадача2", "ОписаниеПодзадача2", 1, 5, "2029-12-21T21:21:21");
        taskManager.updateStatusOfEpic(taskManager.getEpic(1));
        assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(1).getEpicStatus(), "Статус не обновлён");
    }

    @Test
    public void StatusDoneOfEpicWhenEverySubtaskIsDone() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 3, "2029-12-21T21:21:21");
        taskManager.createSubTask("Подзадача2", "ОписаниеПодзадача2", 1, 5, "2029-12-21T21:21:21");
        taskManager.getSubTaskById(2).setStatusSubtask(Status.DONE);
        taskManager.getSubTaskById(3).setStatusSubtask(Status.DONE);
        taskManager.updateStatusOfEpic(taskManager.getEpic(1));
        assertEquals(Status.DONE, taskManager.getEpicById(1).getEpicStatus(), "Статус не обновлён");
    }

    @Test
    public void StatusInProgressOfEpic() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 3, "2029-12-21T21:21:21");
        taskManager.createSubTask("Подзадача2", "ОписаниеПодзадача2", 1, 5, "2029-12-21T21:21:21");
        taskManager.updateStatusOfEpic(taskManager.getEpic(1));
        assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(1).getEpicStatus(), "Статус не обновлён");
    }

    @Test
    public void StatusOfEpicWhenEverySubtaskIsInProgress() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 3, "2029-12-21T21:21:21");
        taskManager.createSubTask("Подзадача2", "ОписаниеПодзадача2", 1, 5, "2029-12-21T21:21:21");
        taskManager.updateStatusOfEpic(taskManager.getEpic(1));
        assertEquals(Status.IN_PROGRESS,taskManager.getEpicById(1).getEpicStatus(), "Статус не обновлён");
    }

}