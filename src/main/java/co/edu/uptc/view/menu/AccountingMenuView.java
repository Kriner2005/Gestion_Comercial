package co.edu.uptc.view.menu;

import co.edu.uptc.i18n.I18n;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.view.ConsoleHelper;

/**
 * AccountingMenuView — Submenú de Contabilidad.
 *
 * Captura: descripción, tipo (I/E) y valor del movimiento.
 * El tipo de movimiento se explica visualmente con las opciones disponibles.
 */
public class AccountingMenuView {

    private final PresenterInterface presenter;
    private final ConsoleHelper      console;

    public AccountingMenuView(PresenterInterface presenter, ConsoleHelper console) {
        this.presenter = presenter;
        this.console   = console;
    }

    // -------------------------------------------------------
    // Ciclo del submenú
    // -------------------------------------------------------

    public void show() {
        boolean running = true;
        while (running) {
            printMenu();
            String option = console.readOption();
            switch (option) {
                case "1" -> captureNewMovement();
                case "2" -> presenter.requestListAccounting();
                case "3" -> presenter.requestExportAccounting();
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
        console.printHeader(I18n.msg("menu.accounting.title"));
        console.print("  1. " + I18n.msg("menu.accounting.add"));
        console.print("  2. " + I18n.msg("menu.accounting.list"));
        console.print("  3. " + I18n.msg("menu.sub.export"));
        console.print("  4. " + I18n.msg("menu.sub.back"));
    }

    // -------------------------------------------------------
    // Captura de datos
    // -------------------------------------------------------

    private void captureNewMovement() {
        console.printBlankLine();
        console.print(I18n.msg("menu.accounting.add_title"));
        console.printSeparator();

        String description = console.readLine(I18n.msg("accounting.input.description"));
        String type        = console.readLine(I18n.msg("accounting.input.type"));
        String amount      = console.readLine(I18n.msg("accounting.input.amount"));

        presenter.requestAddAccounting(description, type, amount);
    }
}