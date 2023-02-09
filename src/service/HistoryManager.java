package service;

import model.Epic;
import model.Node;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {
    void addTask(Task Task);

    List<Task> getHistory();

    void remove(int id);

    ArrayList<Task> getTasks();

    void removeNodeEpic(Node<Epic> node);

    void linkLast(Task task);

    public void removeNode(Node<Task> node);
}