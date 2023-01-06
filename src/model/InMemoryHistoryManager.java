package model;

import tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private ArrayList<Task> history = new ArrayList<>();

    private static final int MAX_HISTORY_SIZE = 10;

    @Override
    public void addTask(Task task) {
        if (history.size() > MAX_HISTORY_SIZE) {
            history.remove(0);
            history.add(task);
        } else history.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }
}
