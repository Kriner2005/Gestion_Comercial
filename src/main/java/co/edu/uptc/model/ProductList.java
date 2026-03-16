package co.edu.uptc.model;

import java.util.List;

public class ProductList {

    private final DoubleList<Product> list = new DoubleList<>();

    public void add(Product product) {
        list.addLast(product);
    }

    public List<Product> toList() {
        return list.toList();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Product findById(int id) {
        for (Product p : list.toList()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}