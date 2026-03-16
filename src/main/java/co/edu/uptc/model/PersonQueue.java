package co.edu.uptc.model;

import java.util.List;

public class PersonQueue {

    private final DoubleList<Person> list = new DoubleList<>();
    private int size = 0;

    public void enqueue(Person person) {
        list.addLast(person);
        size++;
    }

    public Person dequeue() {
        if (isEmpty())
            return null;
        size--;
        return list.removeFirst();
    }

    public Person peek() {
        return list.getFirst();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public List<Person> toList() {
        return list.toList();
    }
}