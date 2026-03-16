package co.edu.uptc.view.menu;

import co.edu.uptc.config.ConfigManager;
import co.edu.uptc.i18n.I18n;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.view.ConsoleHelper;

public class ProductMenuView {

    private final PresenterInterface presenter;
    private final ConsoleHelper console;
    private final ConfigManager config;

    public ProductMenuView(PresenterInterface presenter, ConsoleHelper console) {
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
                case "1" -> captureNewProduct();
                case "2" -> presenter.requestListProducts();
                case "3" -> presenter.requestExportProducts();
                case "4" -> running = false;
                default -> console.printError(I18n.msg("msg.error.invalid_option"));
            }
        }
    }

    private void printMenu() {
        console.printBlankLine();
        console.printHeader(I18n.msg("menu.product.title"));
        console.print("  1. " + I18n.msg("menu.sub.add"));
        console.print("  2. " + I18n.msg("menu.sub.list"));
        console.print("  3. " + I18n.msg("menu.sub.export"));
        console.print("  4. " + I18n.msg("menu.sub.back"));
    }

    private void captureNewProduct() {
        console.printBlankLine();
        console.print(I18n.msg("menu.product.add_title"));
        console.printSeparator();

        double priceMax = config.getDouble("product.price.max", 9_999_999.99);

        String description = console.readLine(I18n.msg("product.input.description"));
        String unit = console.readLine(I18n.msg("product.input.unit"));
        String price = console.readLine(I18n.msg("product.input.price", priceMax));

        presenter.requestAddProduct(description, unit, price);
    }
}