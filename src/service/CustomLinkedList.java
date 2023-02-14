package service;

import org.w3c.dom.Node;

import java.util.*;

public class CustomLinkedList<Task> {

    private Node<Task> head = null;
    private int id = 1;
    private Node<Task> tail = null;
    private Map<Integer, Node<Task>> nodesById = new HashMap<>();

    public void linkLast(Task task, int epicId) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(tail, task, null);
        newNode.nodeId = epicId;
        nodesById.put(id, newNode);
        id++;
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
    }

    public void removeNode(Node<Task> node) {
        if (node.nodeId != 0) {
            for (Node<Task> value : nodesById.values()) {
                if (value.nodeId == node.nodeId) {
                   // nodesById.remove(findKeyByNode(value));почему-то не получается применить remove именно по конкретному значению из метода
                    System.out.println(findKeyByNode(value));
                }
            }
        } else nodesById.remove(findKeyByNode(node));
    }

    public Integer findKeyByNode(Node<Task> node) {

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

        int nodeId;
        Task data;
        Node<Task> next;
        Node<Task> prev;

        public Node(Node<Task> prev, Task data, Node<Task> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;

        }

    }

}