package model;

import exceptions.ManagerSaveException;
import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;
import service.TaskManager;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    public TaskManager taskManager = new InMemoryTaskManager();

    @Test
    public void subtaskShouldHaveEpic() throws ManagerSaveException, IOException {
        taskManager.createEpic("Эпик1", "ОписаниеЭпик№1");
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1,  22, "2026-12-21T21:21:21");
        assertEquals(taskManager.getSubtask(2).getEpicIdOfSubtask(), taskManager.getEpicById(1).getEpicId(), "У подзадачи нет эпика");
    }
}