package co.edu.uptc.model;

public class DoubleList<T> {

    private Node header;
    private Node tail;

    public class Node {

        T data;
        Node nextNode;
        Node beforeNode;

        public Node(T data) {
            this.data = data;
        }
    }

    public void add(T data) {
        Node newNode = new Node(data);
        if (header == null) {
            header = newNode;
            tail = newNode;
            return;
        }
        tail.nextNode = newNode;    
        newNode.beforeNode = tail;
        tail = newNode;
    }
}
