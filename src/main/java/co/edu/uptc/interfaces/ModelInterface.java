package co.edu.uptc.interfaces;

import co.edu.uptc.model.Accounting;
import co.edu.uptc.model.Person;
import co.edu.uptc.model.Product;

import java.util.List;

public interface ModelInterface {

    void addPerson(Person person);

    List<Person> getPersons();

    int getNextPersonId();

    void addProduct(Product product);

    List<Product> getProducts();

    int getNextProductId();

    void addAccounting(Accounting accounting);

    List<Accounting> getAccountingMovements();

    double getTotalBalance();

    int getNextAccountingId();
}