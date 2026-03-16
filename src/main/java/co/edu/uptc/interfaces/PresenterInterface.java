package co.edu.uptc.interfaces;

public interface PresenterInterface {

    // --- Wiring MVP ---
    void setView(ViewInterface view);
    void setModel(ModelInterface model);

    // --- Personas ---
    void requestAddPerson(String name, String lastName, String gender, String birthDate);
    void requestListPersons();
    void requestExportPersons();

    // --- Productos ---
    void requestAddProduct(String description, String unit, String price);
    void requestListProducts();
    void requestExportProducts();

    // --- Contabilidad ---
    void requestAddAccounting(String description, String movementType, String amount);
    void requestListAccounting();
    void requestExportAccounting();
}