package co.edu.uptc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Product — Entidad producto.
 *
 * id: número consecutivo único (int, no String).
 * description: solo mayúsculas — la validación la hace el Presenter.
 * unit: libra, kilo, bulto, tonelada, etc.
 * price: positivo con decimales, tope configurable.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Product {
    private int id;
    private String description;
    private String unit;
    private double price;
}
