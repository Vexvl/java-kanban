package service;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();

    Map<Integer, CustomLinkedList<Task>.Node<Task>> nodesById = new HashMap<>();

    @Override
    public void add(Task task) {
        customLinkedList.linkLast(task);
        nodesById.put(task.getIdOfTask(), customLinkedList.tail);
    }

    @Override
    public void remove(CustomLinkedList.Node node) {
        customLinkedList.removeNode(node, nodesById);
    }

    @Override
    public ArrayList getHistory() {
        return customLinkedList.getHistory(nodesById);
    }
}