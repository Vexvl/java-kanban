package service;

import model.Epic;
import model.Node;
import model.Subtask;
import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int MAX_HISTORY_SIZE = 10;

    private LinkedList<Task> history = new LinkedList<>();

    private HashMap<Integer, Node> hashMapHistory = new HashMap<>();

    @Override
    public void addTask(Task task) {
        hashMapHistory.put(task.getIdOfTask(), new Node<>(task));
        if (history.size() > MAX_HISTORY_SIZE) {
            history.remove(0);
        }
        history.add(task);
    }

    @Override
    public void remove(int id) {
        history.remove(id);
        hashMapHistory.remove(id + 1);
    }

    @Override
    public void removeNode(Node<Task> node) {
        hashMapHistory.remove(node);
    }

    @Override
    public void removeNodeEpic(Node<Epic> node) {
        if (!node.data.getArrayOfSubtasksByEpic().isEmpty()){
            int findSubtask = node.data.getIdOfEpic();
                for (Subtask subtask : node.data.getArrayOfSubtasksByEpic()) {
                    if (subtask.getEpicIdOfSubtask() == findSubtask) {
                        hashMapHistory.remove(subtask.getIdOfSubtask());
                    }
                }
            }
            hashMapHistory.remove(node);
        }

    @Override
    public void linkLast(Task task) {
        if (history.size() > MAX_HISTORY_SIZE) {
            history.remove(0);
        }
        history.addLast(task);
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : history) {
            tasks.add(task);
        }
        return tasks;
    }

    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : history) {
            tasks.add(task);
            System.out.println(task);
        }
        return tasks;
    }
}