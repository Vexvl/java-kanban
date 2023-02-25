package service;

import model.Task;

import java.util.List;
import java.util.Map;

public interface HistoryManager {
    void add(Task task);

    void remove(CustomLinkedList.Node node, Task task);

    List<Task> getHistory();

    Map<Integer, CustomLinkedList<Task>.Node<Task>> getHistoryHash();

    CustomLinkedList.Node getNodeById(int id);

}