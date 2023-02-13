package service;

import model.Task;

import java.util.ArrayList;

public interface HistoryManager {
    void add(Task task);

    void remove(CustomLinkedList.Node node);

    ArrayList<CustomLinkedList<Task>.Node<Task>> getHistory();
}