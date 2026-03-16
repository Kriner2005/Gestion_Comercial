package co.edu.uptc.view;

import co.edu.uptc.i18n.I18n;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.Accounting;
import co.edu.uptc.model.Person;
import co.edu.uptc.model.Product;
import co.edu.uptc.view.menu.AccountingMenuView;
import co.edu.uptc.view.menu.PersonMenuView;
import co.edu.uptc.view.menu.ProductMenuView;

import java.util.List;

/**
 * ConsoleView — Vista principal. Implementa ViewInterface.
 *
 * Responsabilidades:
 *   1. Orquestar el menú principal.
 *   2. Instanciar y delegar a los submenús (PersonMenuView, etc.).
 *   3. Recibir los resultados del Presenter y mostrarlos en pantalla.
 *
 * Cada submenú es una clase separada. ConsoleView no captura datos de personas,
 * productos ni contabilidad; solo sabe qué submenú mostrar según la opción elegida.
 *
 * El ConsoleHelper es la única fuente de Scanner y formateo de consola.
 * Ninguna clase de la capa View hace System.out.println por su cuenta;
 * todo pasa por ConsoleHelper para que el formato sea consistente.
 *
 * LO QUE ESTA CLASE NO HACE:
 *   - Validar datos de negocio.
 *   - Llamar directamente al Model.
 *   - Formatear tablas de personas/productos/contabilidad (métodos show* lo hacen aquí
 *     porque el Presenter le envía los datos ya listos y la View decide el formato visual).
 */
public class ConsoleView implements ViewInterface {

    private PresenterInterface  presenter;
    private final ConsoleHelper console = ConsoleHelper.getInstance();

    // Los submenús se inicializan en start() cuando ya tenemos el presenter
    private PersonMenuView     personMenu;
    private ProductMenuView    productMenu;
    private AccountingMenuView accountingMenu;

    // -------------------------------------------------------
    // Wiring MVP
    // -------------------------------------------------------

    @Override
    public void setPresenter(PresenterInterface presenter) {
        this.presenter = presenter;
    }

    // -------------------------------------------------------
    // Punto de entrada
    // -------------------------------------------------------

    @Override
    public void start() {
        // Crear los submenús una vez que tenemos el presenter
        personMenu     = new PersonMenuView(presenter, console);
        productMenu    = new ProductMenuView(presenter, console);
        accountingMenu = new AccountingMenuView(presenter, console);

        console.printBlankLine();
        console.printHeader(I18n.msg("app.welcome"));

        boolean running = true;
        while (running) {
            printMainMenu();
            String option = console.readOption();
            switch (option) {
                case "1" -> personMenu.show();
                case "2" -> productMenu.show();
                case "3" -> accountingMenu.show();
                case "4" -> {
                    console.printBlankLine();
                    console.print(I18n.msg("msg.goodbye"));
                    running = false;
                }
                default -> console.printError(I18n.msg("msg.error.invalid_option"));
            }
        }
    }

    // -------------------------------------------------------
    // Menú principal
    // -------------------------------------------------------

    private void printMainMenu() {
        console.printBlankLine();
        console.printHeader(I18n.msg("menu.main.title"));
        console.print("  1. " + I18n.msg("menu.main.persons"));
        console.print("  2. " + I18n.msg("menu.main.products"));
        console.print("  3. " + I18n.msg("menu.main.accounting"));
        console.print("  4. " + I18n.msg("menu.main.exit"));
    }

    // -------------------------------------------------------
    // Mensajes del Presenter → pantalla
    // -------------------------------------------------------

    @Override
    public void showMessage(String msg) {
        console.printSuccess(msg);
    }

    @Override
    public void showError(String msg) {
        console.printError(msg);
    }

    // -------------------------------------------------------
    // Mostrar datos: el Presenter llama estos métodos
    // con los datos ya listos. La View solo decide el formato visual.
    // -------------------------------------------------------

    @Override
    public void showPersons(List<Person> persons) {
        console.printBlankLine();
        console.printHeader(I18n.msg("person.list.title"));

        if (persons.isEmpty()) {
            console.print("  " + I18n.msg("person.list.empty"));
            return;
        }

        // Cabecera de tabla
        console.print(String.format("  %-4s | %-20s | %-20s | %-6s | %-12s",
            "ID", I18n.msg("label.name"), I18n.msg("label.lastname"),
            I18n.msg("label.gender"), I18n.msg("label.birthdate")));
        console.printSeparator();

        // Filas
        for (Person p : persons) {
            String genderLabel = (p.getGender() == 'M')
                ? I18n.msg("gender.male")
                : I18n.msg("gender.female");

            console.print(String.format("  %-4d | %-20s | %-20s | %-6s | %-12s",
                p.getId(),
                p.getName(),
                p.getLastName(),
                genderLabel,
                p.getBirthDate().toString()));
        }

        console.print("  " + I18n.msg("list.total_records", persons.size()));
    }

    @Override
    public void showProducts(List<Product> products) {
        console.printBlankLine();
        console.printHeader(I18n.msg("product.list.title"));

        if (products.isEmpty()) {
            console.print("  " + I18n.msg("product.list.empty"));
            return;
        }

        console.print(String.format("  %-4s | %-30s | %-12s | %s",
            "ID", I18n.msg("label.description"),
            I18n.msg("label.unit"), I18n.msg("label.price")));
        console.printSeparator();

        for (Product p : products) {
            console.print(String.format("  %-4d | %-30s | %-12s | %,.2f",
                p.getId(),
                p.getDescription(),
                p.getUnit(),
                p.getPrice()));
        }

        console.print("  " + I18n.msg("list.total_records", products.size()));
    }

    @Override
    public void showAccounting(List<Accounting> movements, double totalBalance) {
        console.printBlankLine();
        console.printHeader(I18n.msg("accounting.list.title"));

        if (movements.isEmpty()) {
            console.print("  " + I18n.msg("accounting.list.empty"));
            return;
        }

        console.print(String.format("  %-4s | %-28s | %-8s | %-15s | %s",
            "ID", I18n.msg("label.description"),
            I18n.msg("label.type"), I18n.msg("label.amount"),
            I18n.msg("label.datetime")));
        console.printSeparator();

        for (Accounting a : movements) {
            String typeLabel = (a.getType() == Accounting.MovementType.INCOME)
                ? I18n.msg("accounting.type.income")
                : I18n.msg("accounting.type.expense");

            console.print(String.format("  %-4d | %-28s | %-8s | %,15.2f | %s",
                a.getId(),
                truncate(a.getDescription(), 28),
                typeLabel,
                a.getAmount(),
                a.getDateTime().toString().replace("T", " ").substring(0, 19)));
        }

        console.printSeparator();
        console.print("  " + I18n.msg("accounting.list.balance", String.format("%,.2f", totalBalance)));
        console.print("  " + I18n.msg("list.total_records", movements.size()));
    }

    // -------------------------------------------------------
    // Utilidades de formato (privadas, solo esta clase)
    // -------------------------------------------------------

    /** Trunca un texto y agrega "…" si supera el máximo de caracteres. */
    private String truncate(String text, int max) {
        if (text == null) return "";
        if (text.length() <= max) return text;
        return text.substring(0, max - 1) + "…";
    }
}