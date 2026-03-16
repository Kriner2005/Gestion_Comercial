package co.edu.uptc.view.menu;

import co.edu.uptc.config.ConfigManager;
import co.edu.uptc.i18n.I18n;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.view.ConsoleHelper;

public class PersonMenuView {

    private final PresenterInterface presenter;
    private final ConsoleHelper console;
    private final ConfigManager config;

    public PersonMenuView(PresenterInterface presenter, ConsoleHelper console) {
        this.presenter = presenter;
        this.console = console;
        this.config = ConfigManager.getInstance();
    }

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
                default -> console.printError(I18n.msg("msg.error.invalid_option"));
            }
        }
    }

    private void printMenu() {
        console.printBlankLine();
        console.printHeader(I18n.msg("menu.person.title"));
        console.print("  1. " + I18n.msg("menu.sub.add"));
        console.print("  2. " + I18n.msg("menu.sub.list"));
        console.print("  3. " + I18n.msg("menu.sub.export"));
        console.print("  4. " + I18n.msg("menu.sub.back"));
    }

    private void captureNewPerson() {
        console.printBlankLine();
        console.print(I18n.msg("menu.person.add_title"));
        console.printSeparator();

        int nameMin = config.getInt("person.name.min", 2);
        int nameMax = config.getInt("person.name.max", 50);
        int lnMin = config.getInt("person.lastname.min", 2);
        int lnMax = config.getInt("person.lastname.max", 50);

        String name = console.readLine(I18n.msg("person.input.name", nameMin, nameMax));
        String lastName = console.readLine(I18n.msg("person.input.lastname", lnMin, lnMax));
        String gender = console.readLine(I18n.msg("person.input.gender"));
        String birthDate = console.readLine(I18n.msg("person.input.birthdate"));

        presenter.requestAddPerson(name, lastName, gender, birthDate);
    }
}