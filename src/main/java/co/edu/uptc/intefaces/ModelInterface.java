package co.edu.uptc.intefaces;

import java.util.List;

import co.edu.uptc.model.Person;
import co.edu.uptc.model.Product;

public interface ModelInterface {
    void addPerson(Person person);
    void addProduct(Product product);
    List<Object> getData();

}
