package service;

import model.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    void remove(CustomLinkedList.Node node, Task task);

    List<Task> getHistory();

    CustomLinkedList.Node getNodeById(int id);

}