package co.edu.uptc.presenter;

import co.edu.uptc.config.ConfigManager;
import co.edu.uptc.i18n.I18n;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.Accounting;
import co.edu.uptc.model.Person;
import co.edu.uptc.model.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

/**
 * MainPresenter — El cerebro del patrón MVP.
 *
 * Responsabilidades:
 *   1. Recibir datos crudos (Strings) desde la View.
 *   2. Validarlos con las reglas de negocio.
 *   3. Transformarlos en objetos del modelo.
 *   4. Llamar al Model para persistir o consultar.
 *   5. Decirle a la View qué mostrar (éxito, error o datos).
 *
 * LO QUE NUNCA HACE:
 *   - Imprimir directamente a consola (eso es la View).
 *   - Conocer las estructuras internas del Model (Cola, Pila, Lista).
 *   - Tomar decisiones de presentación (formato de tabla, colores, etc.).
 */
public class MainPresenter implements PresenterInterface {

    private static final Logger         LOGGER        = Logger.getLogger(MainPresenter.class.getName());
    private final        ConfigManager  config        = ConfigManager.getInstance();
    private final        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final        DateTimeFormatter fileTimestamp = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ViewInterface  view;
    private ModelInterface model;

    // -------------------------------------------------------
    // Wiring MVP
    // -------------------------------------------------------

    @Override
    public void setView(ViewInterface view) {
        this.view = view;
    }

    @Override
    public void setModel(ModelInterface model) {
        this.model = model;
    }

    // -------------------------------------------------------
    // Personas
    // -------------------------------------------------------

    @Override
    public void requestAddPerson(String name, String lastName, String gender, String birthDate) {
        int nameMin     = config.getInt("person.name.min", 2);
        int nameMax     = config.getInt("person.name.max", 50);
        int lastNameMin = config.getInt("person.lastname.min", 2);
        int lastNameMax = config.getInt("person.lastname.max", 50);

        if (!isLengthValid(name, nameMin, nameMax)) {
            view.showError(I18n.msg("msg.error.field_length", I18n.msg("label.name"), nameMin, nameMax));
            return;
        }
        if (!isLengthValid(lastName, lastNameMin, lastNameMax)) {
            view.showError(I18n.msg("msg.error.field_length", I18n.msg("label.lastname"), lastNameMin, lastNameMax));
            return;
        }

        String g = (gender == null) ? "" : gender.trim().toUpperCase();
        if (!g.equals("M") && !g.equals("F")) {
            view.showError(I18n.msg("msg.error.invalid_gender"));
            return;
        }

        LocalDate date = parseDate(birthDate);
        if (date == null) {
            view.showError(I18n.msg("msg.error.invalid_date"));
            return;
        }

        Person person = new Person(
            model.getNextPersonId(),
            capitalize(name),
            capitalize(lastName),
            g.charAt(0),
            date
        );
        model.addPerson(person);
        view.showMessage(I18n.msg("msg.success.add"));
        LOGGER.info("Persona agregada id=" + person.getId());
    }

    @Override
    public void requestListPersons() {
        view.showPersons(model.getPersons());
    }

    @Override
    public void requestExportPersons() {
        String fileName  = "personas_" + LocalDateTime.now().format(fileTimestamp) + ".csv";
        String delimiter = config.get("app.export.delimiter", ",");

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.println("ID" + delimiter + "Nombre" + delimiter + "Apellido"
                       + delimiter + "Genero" + delimiter + "FechaNac");
            for (Person p : model.getPersons()) {
                pw.printf("%d%s%s%s%s%s%c%s%s%n",
                    p.getId(), delimiter, p.getName(), delimiter, p.getLastName(),
                    delimiter, p.getGender(), delimiter, p.getBirthDate());
            }
            view.showMessage(I18n.msg("msg.success.export", fileName));
        } catch (IOException e) {
            LOGGER.severe("Error exportando personas: " + e.getMessage());
            view.showError(I18n.msg("msg.error.export_failed"));
        }
    }

    // -------------------------------------------------------
    // Productos
    // -------------------------------------------------------

    @Override
    public void requestAddProduct(String description, String unit, String price) {
        int    descMax  = config.getInt("product.description.max", 200);
        double priceMax = config.getDouble("product.price.max", 9_999_999.99);

        if (!isLengthValid(description, 1, descMax)) {
            view.showError(I18n.msg("msg.error.field_length", I18n.msg("label.description"), 1, descMax));
            return;
        }
        if (!isUpperCase(description)) {
            view.showError(I18n.msg("msg.error.uppercase_only"));
            return;
        }
        if (!isLengthValid(unit, 1, 50)) {
            view.showError(I18n.msg("msg.error.min_length", 1));
            return;
        }

        double parsedPrice = parsePositiveDouble(price, priceMax);
        if (parsedPrice < 0) {
            view.showError(I18n.msg("msg.error.price_invalid", priceMax));
            return;
        }

        Product product = new Product(
            model.getNextProductId(),
            description.trim(),
            unit.trim(),
            parsedPrice
        );
        model.addProduct(product);
        view.showMessage(I18n.msg("msg.success.add"));
    }

    @Override
    public void requestListProducts() {
        view.showProducts(model.getProducts());
    }

    @Override
    public void requestExportProducts() {
        String fileName  = "productos_" + LocalDateTime.now().format(fileTimestamp) + ".csv";
        String delimiter = config.get("app.export.delimiter", ",");

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.println("ID" + delimiter + "Descripcion" + delimiter + "Unidad" + delimiter + "Precio");
            for (Product p : model.getProducts()) {
                pw.printf("%d%s%s%s%s%s%.2f%n",
                    p.getId(), delimiter, p.getDescription(), delimiter, p.getUnit(), delimiter, p.getPrice());
            }
            view.showMessage(I18n.msg("msg.success.export", fileName));
        } catch (IOException e) {
            view.showError(I18n.msg("msg.error.export_failed"));
        }
    }

    // -------------------------------------------------------
    // Contabilidad
    // -------------------------------------------------------

    @Override
    public void requestAddAccounting(String description, String movementType, String amount) {
        if (!isLengthValid(description, 1, 200)) {
            view.showError(I18n.msg("msg.error.min_length", 1));
            return;
        }

        Accounting.MovementType type = parseMovementType(movementType);
        if (type == null) {
            view.showError(I18n.msg("msg.error.invalid_movement_type"));
            return;
        }

        double parsedAmount = parsePositiveDouble(amount, Double.MAX_VALUE);
        if (parsedAmount < 0) {
            view.showError(I18n.msg("msg.error.positive_number"));
            return;
        }

        Accounting accounting = new Accounting(
            model.getNextAccountingId(),
            description.trim(),
            type,
            parsedAmount,
            LocalDateTime.now()
        );
        model.addAccounting(accounting);
        view.showMessage(I18n.msg("msg.success.add"));
    }

    @Override
    public void requestListAccounting() {
        view.showAccounting(model.getAccountingMovements(), model.getTotalBalance());
    }

    @Override
    public void requestExportAccounting() {
        String fileName  = "contabilidad_" + LocalDateTime.now().format(fileTimestamp) + ".csv";
        String delimiter = config.get("app.export.delimiter", ",");

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.println("ID" + delimiter + "Descripcion" + delimiter + "Tipo"
                       + delimiter + "Valor" + delimiter + "FechaHora");
            for (Accounting a : model.getAccountingMovements()) {
                pw.printf("%d%s%s%s%s%s%.2f%s%s%n",
                    a.getId(), delimiter, a.getDescription(), delimiter,
                    a.getType(), delimiter, a.getAmount(), delimiter, a.getDateTime());
            }
            view.showMessage(I18n.msg("msg.success.export", fileName));
        } catch (IOException e) {
            view.showError(I18n.msg("msg.error.export_failed"));
        }
    }

    // -------------------------------------------------------
    // Helpers privados de validación / parseo
    // -------------------------------------------------------

    private boolean isLengthValid(String value, int min, int max) {
        if (value == null) return false;
        int len = value.trim().length();
        return len >= min && len <= max;
    }

    private boolean isUpperCase(String value) {
        if (value == null || value.isBlank()) return false;
        String t = value.trim();
        return t.equals(t.toUpperCase());
    }

    /** Retorna -1.0 como señal de error (precio siempre > 0). */
    private double parsePositiveDouble(String value, double max) {
        if (value == null || value.isBlank()) return -1.0;
        try {
            double parsed = Double.parseDouble(value.trim().replace(",", "."));
            if (parsed <= 0 || parsed > max) return -1.0;
            return parsed;
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }

    private LocalDate parseDate(String raw) {
        if (raw == null) return null;
        try {
            return LocalDate.parse(raw.trim(), dateFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private Accounting.MovementType parseMovementType(String raw) {
        if (raw == null) return null;
        return switch (raw.trim().toUpperCase()) {
            case "I" -> Accounting.MovementType.INCOME;
            case "E" -> Accounting.MovementType.EXPENSE;
            default  -> null;
        };
    }

    /** Primera letra en mayúscula, resto en minúscula. */
    private String capitalize(String value) {
        if (value == null || value.isBlank()) return value;
        String t = value.trim().toLowerCase();
        return Character.toUpperCase(t.charAt(0)) + t.substring(1);
    }
}