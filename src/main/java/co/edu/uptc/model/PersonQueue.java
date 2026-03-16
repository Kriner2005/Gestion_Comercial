package co.edu.uptc.model;


import java.util.List;

/**
 * PersonQueue — Cola de personas con comportamiento FIFO.
 *
 * <p>FIFO = First In, First Out: el primero en entrar es el primero en salir.
 * Es el comportamiento correcto de una "cola" (como en un banco).
 *
 * <p>Nota académica: el enunciado dice "cola (LIFO)" lo cual es una contradicción.
 * LIFO es una pila (Stack). Una cola es FIFO por definición.
 * Se implementa FIFO que es el comportamiento correcto de una cola.
 *
 * <p>Se usa la lista doblemente enlazada genérica {@link DoubleList} internamente.
 */
public class PersonQueue {

    private final DoubleList<Person> list = new DoubleList<>();
    private int size = 0;

    /**
     * Encola una persona al final de la cola (enqueue).
     */
    public void enqueue(Person person) {
        list.addLast(person);
        size++;
    }

    /**
     * Desencola la persona al frente de la cola (dequeue).
     * @return la persona más antigua en la cola, o null si está vacía.
     */
    public Person dequeue() {
        if (isEmpty()) return null;
        size--;
        return list.removeFirst();
    }

    /**
     * Consulta la persona al frente sin eliminarla (peek).
     */
    public Person peek() {
        return list.getFirst();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Retorna todos los elementos como lista para listarlos en pantalla.
     * No modifica la cola.
     */
    public List<Person> toList() {
        return list.toList();
    }
}