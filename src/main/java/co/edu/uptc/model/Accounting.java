package co.edu.uptc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Accounting — Movimiento contable.
 *
 * <p>Nota sobre {@code MovementType}:
 * Usamos un enum en lugar de String o char para el tipo de movimiento.
 * Esto evita valores inválidos y hace el código autodocumentado.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Accounting {

    public enum MovementType {
        INCOME,   // Ingreso
        EXPENSE   // Egreso
    }

    private int id;
    private String description;
    private MovementType type;
    private double amount;           // Siempre positivo
    private LocalDateTime dateTime;  // LocalDateTime en vez de Date (API moderna)
}