package co.edu.uptc.view;

import co.edu.uptc.i18n.I18n;

import java.util.Scanner;

/**
 * ConsoleHelper — Utilidad compartida para leer input y escribir en consola.
 *
 * Una sola instancia de Scanner en todo el programa (Singleton).
 * Centraliza las operaciones básicas de I/O para que los menús no
 * dupliquen código ni manejen el Scanner por su cuenta.
 *
 * Responsabilidades:
 *   - Proveer un único Scanner compartido.
 *   - Leer líneas y limpiar espacios.
 *   - Imprimir mensajes de éxito, error y líneas separadoras.
 *   - Leer opciones de menú (delegar la validación de opción a cada menú).
 *
 * LO QUE NO HACE:
 *   - Validar lógica de negocio.
 *   - Llamar al Presenter.
 *   - Conocer nada de los modelos.
 */
public class ConsoleHelper {

    private static ConsoleHelper instance;
    private final Scanner scanner = new Scanner(System.in);

    private ConsoleHelper() {}

    public static ConsoleHelper getInstance() {
        if (instance == null) {
            instance = new ConsoleHelper();
        }
        return instance;
    }

    // -------------------------------------------------------
    // Lectura de input
    // -------------------------------------------------------

    /** Lee una línea completa y elimina espacios al inicio/fin. */
    public String readLine() {
        return scanner.nextLine().trim();
    }

    /** Muestra el prompt y lee la respuesta en la misma línea. */
    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /** Lee sin mostrar prompt. Útil cuando el prompt ya fue impreso. */
    public String readOption() {
        System.out.print(I18n.msg("menu.option_prompt"));
        return scanner.nextLine().trim();
    }

    // -------------------------------------------------------
    // Salida a consola
    // -------------------------------------------------------

    public void print(String text) {
        System.out.println(text);
    }

    public void printBlankLine() {
        System.out.println();
    }

    /** Mensaje de éxito con prefijo visual. */
    public void printSuccess(String msg) {
        System.out.println("  [OK] " + msg);
    }

    /** Mensaje de error a stderr con prefijo visual. */
    public void printError(String msg) {
        System.err.println("  [ERROR] " + msg);
    }

    /** Separador horizontal de ancho fijo. */
    public void printSeparator() {
        System.out.println("-".repeat(60));
    }

    /** Separador doble para cabeceras de sección. */
    public void printHeader(String title) {
        String line = "=".repeat(60);
        System.out.println(line);
        System.out.printf("  %s%n", title);
        System.out.println(line);
    }
}