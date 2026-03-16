package co.edu.uptc.interfaces;

import co.edu.uptc.model.Accounting;
import co.edu.uptc.model.Person;
import co.edu.uptc.model.Product;

import java.util.List;

/**
 * ViewInterface — Contrato de la Vista.
 *
 * El Presenter solo llama estos métodos para comunicarse con la View.
 * La View recibe datos ya procesados y los muestra.
 * La View NUNCA toma decisiones de negocio ni valida nada.
 */
public interface ViewInterface {

    void setPresenter(PresenterInterface presenter);

    /** Arranca el ciclo principal de menús. */
    void start();

    // --- Mensajes ---
    void showMessage(String msg);
    void showError(String msg);

    // --- Mostrar datos (el Presenter ya los calculó/filtró) ---
    void showPersons(List<Person> persons);
    void showProducts(List<Product> products);
    void showAccounting(List<Accounting> movements, double totalBalance);
}