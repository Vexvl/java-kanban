package managers;

        import tasks.Task;

        import java.util.ArrayList;

public interface HistoryManager {
    void addTask(Task Task);

    ArrayList<Task> getHistory();
}