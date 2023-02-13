package service;


import java.util.ArrayList;
import java.util.Map;
public class CustomLinkedList<Task> {

    Node<Task> head = null;
    Node<Task> tail = null;

    public void linkLast(Task task) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(tail, task, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
    }

    public void removeNode(Node<Task> node, Map<Integer, Node<Task>> nodesById) {
        if (nodesById.containsKey(node)) {
            nodesById.remove(node);
        }
    }

    public ArrayList getHistory(Map<Integer, Node<Task>> nodesById) {
        ArrayList<Node<Task>> tasks = new ArrayList<>();
        for (Node<Task> task : nodesById.values()) {
            tasks.add(task);
        }
        return tasks;
    }

    class Node<Task> {

        public Task data;
        public Node<Task> next;
        public Node<Task> prev;

        public Node(Node<Task> prev, Task data, Node<Task> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

    }
}