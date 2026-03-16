package co.edu.uptc.model;

import java.util.List;

public class AccountingStack {

    private final DoubleList<Accounting> list = new DoubleList<>();
    private int size = 0;

    public void push(Accounting accounting) {
        list.addLast(accounting);
        size++;
    }

    public Accounting pop() {
        if (isEmpty())
            return null;
        size--;
        return list.removeLast();
    }

    public Accounting peek() {
        return list.getLast();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public List<Accounting> toList() {
        return list.toListReversed();
    }

    public double getTotalBalance() {
        return list.toList().stream()
                .mapToDouble(a -> a.getType() == Accounting.MovementType.INCOME
                        ? a.getAmount()
                        : -a.getAmount())
                .sum();
    }
}