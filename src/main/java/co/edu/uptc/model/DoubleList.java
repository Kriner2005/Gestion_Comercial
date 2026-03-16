package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DoubleList — Lista doblemente enlazada genérica.
 *
 * <p>Provee las operaciones necesarias para soportar tanto la cola (Queue)
 * como la pila (Stack) y la lista de productos.
 *
 * <p>El tipo genérico {@code <T>} permite reutilizar esta estructura
 * para cualquier tipo de dato.
 *
 * @param <T> el tipo de dato almacenado en los nodos
 */
public class DoubleList<T> {

    // Los nodos son privados; nadie fuera de esta clase debe manipularlos
    private Node<T> head;
    private Node<T> tail;
    private int size;

    // -------------------------------------------------------
    // Nodo interno estático y genérico
    // Static: no necesita referencia a la instancia de DoubleList
    // -------------------------------------------------------
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        Node(T data) {
            this.data = data;
        }
    }

    // -------------------------------------------------------
    // Operaciones de inserción
    // -------------------------------------------------------

    /** Agrega al final (para enqueue y push). */
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    /** Agrega al inicio. */
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // -------------------------------------------------------
    // Operaciones de eliminación
    // -------------------------------------------------------

    /** Elimina y retorna el primer elemento (para dequeue). */
    public T removeFirst() {
        if (isEmpty()) return null;
        T data = head.data;
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return data;
    }

    /** Elimina y retorna el último elemento (para pop). */
    public T removeLast() {
        if (isEmpty()) return null;
        T data = tail.data;
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return data;
    }

    // -------------------------------------------------------
    // Operaciones de consulta
    // -------------------------------------------------------

    public T getFirst() {
        return isEmpty() ? null : head.data;
    }

    public T getLast() {
        return isEmpty() ? null : tail.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Retorna todos los elementos como List (orden normal: head → tail).
     * No modifica la lista enlazada.
     */
    public List<T> toList() {
        List<T> result = new ArrayList<>(size);
        Node<T> current = head;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Retorna los elementos en orden inverso (tail → head).
     * Útil para mostrar la pila con el elemento más reciente primero.
     */
    public List<T> toListReversed() {
        List<T> result = new ArrayList<>(size);
        Node<T> current = tail;
        while (current != null) {
            result.add(current.data);
            current = current.prev;
        }
        return Collections.unmodifiableList(result);
    }
}