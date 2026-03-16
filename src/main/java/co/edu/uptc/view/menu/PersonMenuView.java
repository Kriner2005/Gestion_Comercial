package co.edu.uptc.view.menu;

import co.edu.uptc.config.ConfigManager;
import co.edu.uptc.i18n.I18n;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.view.ConsoleHelper;

/**
 * PersonMenuView — Submenú de Personas.
 *
 * Responsabilidades:
 *   - Mostrar el submenú de personas.
 *   - Capturar el input del usuario para cada acción.
 *   - Delegar TODA la lógica al Presenter.
 *
 * Esta clase no valida nada. Si el nombre es muy corto o la fecha
 * tiene formato incorrecto, eso lo detecta el Presenter, no aquí.
 * La View solo pregunta y pasa los datos crudos.
 *
 * Por qué separar esto de ConsoleView?
 *   Si PersonMenuView tuviera 10 campos de captura, la ConsoleView principal
 *   se volvería un archivo de 800 líneas ilegible. Cada menú tiene
 *   su propia clase: cohesión alta, acoplamiento bajo.
 */
public class PersonMenuView {

    private final PresenterInterface presenter;
    private final ConsoleHelper      console;
    private final ConfigManager      config;

    public PersonMenuView(PresenterInterface presenter, ConsoleHelper console) {
        this.presenter = presenter;
        this.console   = console;
        this.config    = ConfigManager.getInstance();
    }

    // -------------------------------------------------------
    // Ciclo del submenú
    // -------------------------------------------------------

    /**
     * Ejecuta el submenú en un loop hasta que el usuario elija "Volver".
     * Retorna cuando el usuario selecciona la opción de regreso.
     */
    public void show() {
        boolean running = true;
        while (running) {
            printMenu();
            String option = console.readOption();
            switch (option) {
                case "1" -> captureNewPerson();
                case "2" -> presenter.requestListPersons();
                case "3" -> presenter.requestExportPersons();
                case "4" -> running = false;
                default  -> console.printError(I18n.msg("msg.error.invalid_option"));
            }
        }
    }

    // -------------------------------------------------------
    // Presentación del menú
    // -------------------------------------------------------

    private void printMenu() {
        console.printBlankLine();
        console.printHeader(I18n.msg("menu.person.title"));
        console.print("  1. " + I18n.msg("menu.sub.add"));
        console.print("  2. " + I18n.msg("menu.sub.list"));
        console.print("  3. " + I18n.msg("menu.sub.export"));
        console.print("  4. " + I18n.msg("menu.sub.back"));
    }

    // -------------------------------------------------------
    // Captura de datos (sin validación, solo lectura)
    // -------------------------------------------------------

    private void captureNewPerson() {
        console.printBlankLine();
        console.print(I18n.msg("menu.person.add_title"));
        console.printSeparator();

        int nameMin = config.getInt("person.name.min", 2);
        int nameMax = config.getInt("person.name.max", 50);
        int lnMin   = config.getInt("person.lastname.min", 2);
        int lnMax   = config.getInt("person.lastname.max", 50);

        String name      = console.readLine(I18n.msg("person.input.name", nameMin, nameMax));
        String lastName  = console.readLine(I18n.msg("person.input.lastname", lnMin, lnMax));
        String gender    = console.readLine(I18n.msg("person.input.gender"));
        String birthDate = console.readLine(I18n.msg("person.input.birthdate"));

        // Pasa los datos crudos al Presenter. Él decide si son válidos.
        presenter.requestAddPerson(name, lastName, gender, birthDate);
    }
}