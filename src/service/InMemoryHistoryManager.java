package service;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        customLinkedList.linkLast(task, task.getEpicId(), task.getIdOfTask());
    }

    @Override
    public void remove(CustomLinkedList.Node node) {
        customLinkedList.removeNode(node);
    }

    @Override
    public List<Task> getHistory() {
        return customLinkedList.getHistory();
    }

    @Override
    public CustomLinkedList.Node getNodeById(int id) {
        return customLinkedList.getNodeById(id);
    }

}