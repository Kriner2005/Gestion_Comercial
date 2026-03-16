package co.edu.uptc.model;

import java.util.List;

/**
 * AccountingStack — Pila de movimientos contables con comportamiento LIFO.
 *
 * <p>LIFO = Last In, First Out: el último en entrar es el primero en salir.
 * El movimiento más reciente está en el tope de la pila.
 *
 * <p>Usa {@link DoubleList} internamente.
 */
public class AccountingStack {

    private final DoubleList<Accounting> list = new DoubleList<>();
    private int size = 0;

    /**
     * Empuja un movimiento al tope de la pila (push).
     */
    public void push(Accounting accounting) {
        list.addLast(accounting);
        size++;
    }

    /**
     * Saca el movimiento del tope de la pila (pop).
     * @return el movimiento más reciente, o null si está vacía.
     */
    public Accounting pop() {
        if (isEmpty()) return null;
        size--;
        return list.removeLast();
    }

    /**
     * Consulta el tope sin eliminarlo (peek).
     */
    public Accounting peek() {
        return list.getLast();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Retorna todos los movimientos para listarlos.
     * El orden es LIFO: el más reciente primero.
     */
    public List<Accounting> toList() {
        return list.toListReversed();
    }

    /**
     * Calcula el saldo total: ingresos - egresos.
     */
    public double getTotalBalance() {
        return list.toList().stream()
            .mapToDouble(a -> a.getType() == Accounting.MovementType.INCOME
                ? a.getAmount()
                : -a.getAmount())
            .sum();
    }
}