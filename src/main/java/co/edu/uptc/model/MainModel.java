package co.edu.uptc.model;

import co.edu.uptc.interfaces.ModelInterface;

import java.util.List;

public class MainModel implements ModelInterface {

    private final PersonQueue personQueue = new PersonQueue();
    private final AccountingStack accountingStack = new AccountingStack();
    private final ProductList productList = new ProductList();

    private int personIdCounter = 1;
    private int productIdCounter = 1;
    private int accountingIdCounter = 1;

    @Override
    public void addPerson(Person person) {
        personQueue.enqueue(person);
        personIdCounter++;
    }

    @Override
    public List<Person> getPersons() {
        return personQueue.toList();
    }

    @Override
    public int getNextPersonId() {
        return personIdCounter;
    }

    @Override
    public void addProduct(Product product) {
        productList.add(product);
        productIdCounter++;
    }

    @Override
    public List<Product> getProducts() {
        return productList.toList();
    }

    @Override
    public int getNextProductId() {
        return productIdCounter;
    }

    @Override
    public void addAccounting(Accounting a) {
        accountingStack.push(a);
        accountingIdCounter++;
    }

    @Override
    public List<Accounting> getAccountingMovements() {
        return accountingStack.toList();
    }

    @Override
    public double getTotalBalance() {
        return accountingStack.getTotalBalance();
    }

    @Override
    public int getNextAccountingId() {
        return accountingIdCounter;
    }
}