package co.edu.uptc.interfaces;

import co.edu.uptc.model.Accounting;
import co.edu.uptc.model.Person;
import co.edu.uptc.model.Product;

import java.util.List;

/**
 * ModelInterface — Contrato de la capa de datos/negocio.
 *
 * El Presenter solo conoce esta interfaz; nunca la implementación concreta.
 */
public interface ModelInterface {

    // --- Personas ---
    void addPerson(Person person);
    List<Person> getPersons();
    int getNextPersonId();

    // --- Productos ---
    void addProduct(Product product);
    List<Product> getProducts();
    int getNextProductId();

    // --- Contabilidad ---
    void addAccounting(Accounting accounting);
    List<Accounting> getAccountingMovements();
    double getTotalBalance();
    int getNextAccountingId();
}