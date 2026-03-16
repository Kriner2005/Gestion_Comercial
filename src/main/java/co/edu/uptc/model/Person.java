package co.edu.uptc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Person — Entidad de persona.
 *
 * <p>Notas de buenas prácticas:
 *
 * <p>1. {@code LocalDate} en vez de {@code java.util.Date}:
 *    La clase Date está deprecada en muchos aspectos.
 *    LocalDate (Java 8+) es inmutable, no tiene zona horaria implícita,
 *    y tiene una API mucho más limpia.
 *
 * <p>2. {@code char gender}: suficiente para M/F. Un enum sería más robusto
 *    si hubiera más opciones en el futuro.
 *
 * <p>3. Las anotaciones de Lombok (@Getter, @Setter, etc.) evitan
 *    código boilerplate. El compilador las procesa y genera los métodos.
 *    En producción real muchas empresas usan Records (Java 16+) para esto.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Person {
    private int id;
    private String name;
    private String lastName;
    private char gender;
    private LocalDate birthDate;  // LocalDate es inmutable y más preciso que Date
}