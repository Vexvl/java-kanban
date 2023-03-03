package model;

import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;

import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {

    private Epic epic = new Epic("Эпик1", "ОписаниеЭпик1", 1, Status.NEW, 34, "2023-21-21");
    private InMemoryTaskManager taskManager = new InMemoryTaskManager();
    @Test
    public void ShallReturnNoSubtasks(){
        assertNull(epic.getSubtasks());
    }

    @Test
    public void StatusInProgressOfEpicWhenEverySubtaskIsNew(){
        Subtask subtask = new Subtask("Подзадача1", "ОписаниеПодзадача1", 2, Status.NEW, 3,34, "1101-20-20");
        Subtask subtask2 = new Subtask("Подзадача2", "ОписаниеПодзадача2", 3, Status.NEW, 3, 90, "2020-10-20");
        taskManager.updateStatusOfEpic(1);
        assertEquals(Status.NEW, epic.getEpicStatus(), "Статус не обновлён");
    }

    @Test
    public void StatusDoneOfEpicWhenEverySubtaskIsDone(){
        Subtask subtask = new Subtask("Подзадача1", "ОписаниеПодзадача1", 2, Status.DONE, 3, 11, "3003-30-30");
        Subtask subtask2 = new Subtask("Подзадача2", "ОписаниеПодзадача2", 3, Status.DONE, 3, 95, "3032-30-30");
        taskManager.updateStatusOfEpic(1);
        assertEquals(Status.DONE, epic.getEpicStatus(), "Статус не обновлён");
    }

    @Test
    public void StatusInProgressOfEpic(){
        Subtask subtask = new Subtask("Подзадача1", "ОписаниеПодзадача1", 2, Status.NEW, 3, 240, "2029-12-21T21:21:21");
        Subtask subtask2 = new Subtask("Подзадача2", "ОписаниеПодзадача2", 3, Status.DONE, 3, 240, "2029-12-21T21:21:21");
        taskManager.updateStatusOfEpic(1);
        assertEquals(Status.IN_PROGRESS, epic.getEpicStatus(), "Статус не обновлён");
    }

    @Test
    public void StatusOfEpicWhenEverySubtaskIsInProgress(){
        Subtask subtask = new Subtask("Подзадача1", "ОписаниеПодзадача1", 2, Status.IN_PROGRESS, 3, 240, "2029-12-21T21:21:21");
        Subtask subtask2 = new Subtask("Подзадача2", "ОписаниеПодзадача2", 3, Status.IN_PROGRESS, 3, 240, "2029-12-21T21:21:21");
        taskManager.updateStatusOfEpic(1);
        assertEquals(Status.IN_PROGRESS,epic.getEpicStatus(), "Статус не обновлён");
    }

}