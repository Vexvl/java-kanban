package managers;

import tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private ArrayList<Task> history = new ArrayList<>();

    @Override
    public void addTask(Task task) {
        history.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        if (history.size() > 10) {
            int size = history.size();
            ArrayList<Task> tenlastRecentlyViewed = new ArrayList<>();
            for (int a = 10; a >= 0; a--) {
                tenlastRecentlyViewed.add(history.get(size - a));
            }
            return tenlastRecentlyViewed;
        } else return history;
    }
}
