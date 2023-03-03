package model;

import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;
import service.TaskManager;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    public Epic epic = new Epic("Эпик1", "ОписаниеЭпик1", 1, Status.NEW, 240, "2029-12-21T21:21:21");
    public TaskManager taskManager = new InMemoryTaskManager();

    @Test
    public void IfHasEpic() throws ManagerSaveException, IOException {
        taskManager.createSubTask("Подзадача1", "ОписаниеПодзадача1", 1, 240, "2029-12-21T21:21:21");
        assertEquals(taskManager.getSubtask(2).getEpicIdOfSubtask(), epic.getEpicId());
    }
}