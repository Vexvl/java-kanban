package service;

import java.util.*;

public class CustomLinkedList<Task> {

    private Node<Task> head = null;
    private ArrayList<Integer> idToRemoveFromHash = new ArrayList<>();
    private Node<Task> tail = null;
    private Map<Integer, Node<Task>> nodesById = new HashMap<>();

    public void linkLast(Task task, int epicId, int idOfTask, boolean ifSubtask) {
        Node<Task> oldTail = tail;
        Node<Task> newNode = new Node<>(tail, task, null);
        newNode.nodesEpicId = epicId;
        newNode.ifSubtask = ifSubtask;
        nodesById.put(idOfTask, newNode);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
    }

    public void removeNode(Node<Task> node) {
        if (node.nodesEpicId != 0 && !node.ifSubtask) {
            for (Node<Task> value : nodesById.values()) {
                if (value.nodesEpicId == node.nodesEpicId) {
                    idToRemoveFromHash.add(findKeyByNode(value));
                }
            }
            removeFromHash(idToRemoveFromHash);
        } else nodesById.remove(findKeyByNode(node));
    }

    private void removeFromHash(ArrayList<Integer> idToRemoveFromHash) {
        for (Integer value : idToRemoveFromHash) {
            nodesById.remove(value);
        }
        idToRemoveFromHash.clear();
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
        for (Node<Task> task : nodesById.values()) {
            tasks.add(task);
        }
        return (List<Task>) tasks;
    }

    public Node<Task> getNodeById(int id) {
        if (nodesById.containsKey(id)) {
            return nodesById.get(id);
        }
        return null;
    }


    class Node<Task> {

        int nodesEpicId;// проверка по эпику

        boolean ifSubtask;
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

    }

}