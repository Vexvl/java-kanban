package service;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        customLinkedList.linkLast(task, task.getIdOfTask());
    }

    @Override
    public void remove(CustomLinkedList.Node node, Task task) {
        customLinkedList.removeNode(node, task);
    }

    @Override
    public List<Task> getHistory() {
        return customLinkedList.getHistory();
    }

    @Override
    public Map<Integer, CustomLinkedList<Task>.Node<Task>> getHistoryHash(){
        return customLinkedList.getHistoryHash();
    }

    @Override
    public CustomLinkedList.Node getNodeById(int id) {
        return customLinkedList.getNodeById(id);
    }

}