package service;

import model.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomLinkedList<Task> {

    private Node<Task> head = null;
    private Node<Task> tail = null;
    private Map<Integer, Node<Task>> nodesById = new HashMap<>();

    public void linkLast(Task task, int idOfTask) {
        Node<Task> oldTail = tail;
        Node<Task> newNode = new Node<>(tail, task, null);
        nodesById.put(idOfTask, newNode);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
    }

    public void removeNode(Node<Task> node, model.Task task) {
        if (!node.getType(task).equals(Type.SUBTASK) && node.getEpicId(task) != 0) {
            List<Integer> removeIds = new ArrayList<>();
            for (Node<Task> value : nodesById.values()) {
                if (value.getEpicId((model.Task) value.data) == task.getEpicId()) {
                    removeIds.add(findKeyByNode(value));
                }
            }
            removeFromHash(removeIds);
        } else nodesById.remove(findKeyByNode(node));
    }

    public Map<Integer, Node<Task>> getNodesById() {
        return nodesById;
    }


    private void removeFromHash(List<Integer> removeIds) {
        for (Integer value : removeIds) {
            nodesById.remove(value);
        }
    }

    private Integer findKeyByNode(Node<Task> node) {
        for (Integer key : nodesById.keySet()) {
            if (node.equals(nodesById.get(key))) {
                return key;
            }
        }
        return null;
    }

    public List<Task> getHistory() {
        List<Node<Task>> tasks = new ArrayList<>();
        for (Node<Task> node : nodesById.values()) {
            tasks.add(node);
        }
        return (List<Task>) tasks;
    }

    public Map<Integer, Node<Task>> getHistoryHash() {
        return nodesById;
    }

    public Node<Task> getNodeById(int id) {
        if (nodesById.containsKey(id)) {
            return nodesById.get(id);
        }
        return null;
    }


    public class Node<Task> {

        Task data;
        Node<Task> next;
        Node<Task> prev;

        public Node(Node<Task> prev, Task data, Node<Task> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return data.toString();
        }

        public Type getType(model.Task task) {
            return task.getType();
        }

        public Integer getEpicId(model.Task task) {
            return task.getEpicId();
        }

    }

}