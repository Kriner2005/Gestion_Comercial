package co.edu.uptc.model;

import java.util.List;

/**
 * ProductList — Lista de productos con acceso libre.
 *
 * <p>A diferencia de la cola (personas) y la pila (contabilidad),
 * los productos no tienen restricción de orden de acceso.
 * Se puede agregar al inicio, al final, o en cualquier posición.
 *
 * <p>Operaciones implementadas:
 * - Agregar al final (más común)
 * - Listar todos
 * - Buscar por ID
 * - Eliminar por ID (no pedido en el enunciado, pero útil para completitud)
 */
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

    /**
     * Busca un producto por ID.
     * Recorre la lista completa: O(n). Aceptable para volúmenes académicos.
     */
    public Product findById(String id) {
        for (Product p : list.toList()) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
}