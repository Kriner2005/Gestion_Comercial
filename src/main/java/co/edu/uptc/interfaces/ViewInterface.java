package co.edu.uptc.interfaces;

import co.edu.uptc.model.Accounting;
import co.edu.uptc.model.Person;
import co.edu.uptc.model.Product;

import java.util.List;

public interface ViewInterface {

    void setPresenter(PresenterInterface presenter);

    void start();

    void showMessage(String msg);
    void showError(String msg);

    void showPersons(List<Person> persons);
    void showProducts(List<Product> products);
    void showAccounting(List<Accounting> movements, double totalBalance);
}