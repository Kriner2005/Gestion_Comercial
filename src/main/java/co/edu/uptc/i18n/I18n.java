package co.edu.uptc.i18n;

import co.edu.uptc.config.ConfigManager;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * I18n — Gestor de internacionalización.
 *
 * <p>Centraliza el acceso a los archivos messages_XX.properties.
 * El idioma se determina desde ConfigManager (clave "app.language").
 *
 * <p>Soporta parámetros dinámicos con MessageFormat:
 * <pre>
 *   // En el .properties:  person.input.name=Nombre ({0}-{1} caracteres):
 *   I18n.t("person.input.name", 2, 50)  →  "Nombre (2-50 caracteres):"
 * </pre>
 *
 * <p>Patrón Singleton: una sola instancia por JVM.
 */
public class I18n {

    private static final Logger LOGGER = Logger.getLogger(I18n.class.getName());

    // Nombre base de los archivos de mensajes (sin _es, _en, ni .properties)
    private static final String BUNDLE_BASE = "i18n/messages";

    private static I18n instance;
    private ResourceBundle bundle;

    // -------------------------------------------------------
    // Constructor privado: carga el bundle según el idioma config
    // -------------------------------------------------------
    private I18n() {
        String lang = ConfigManager.getInstance().get("app.language", "es");
        Locale locale = Locale.forLanguageTag(lang);
        loadBundle(locale);
    }

    public static I18n getInstance() {
        if (instance == null) {
            synchronized (I18n.class) {
                if (instance == null) {
                    instance = new I18n();
                }
            }
        }
        return instance;
    }

    // -------------------------------------------------------
    // Carga del ResourceBundle
    // -------------------------------------------------------
    private void loadBundle(Locale locale) {
        try {
            bundle = ResourceBundle.getBundle(BUNDLE_BASE, locale);
            LOGGER.info("Bundle cargado para locale: " + locale);
        } catch (MissingResourceException e) {
            LOGGER.warning("Bundle no encontrado para " + locale + ". Usando español por defecto.");
            bundle = ResourceBundle.getBundle(BUNDLE_BASE, new Locale("es"));
        }
    }

    // -------------------------------------------------------
    // Métodos de acceso a mensajes
    // -------------------------------------------------------

    /**
     * Obtiene un mensaje por su clave, sin parámetros.
     * Si la clave no existe, retorna la clave entre corchetes como fallback visible.
     */
    public String t(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            LOGGER.warning("Clave i18n no encontrada: " + key);
            return "[" + key + "]";  // Fallback visible: alerta al desarrollador
        }
    }

    /**
     * Obtiene un mensaje con parámetros dinámicos usando MessageFormat.
     *
     * <p>Ejemplo en .properties: {@code msg.error.min_length=Mínimo {0} caracteres.}
     * <p>Uso en Java: {@code I18n.getInstance().t("msg.error.min_length", 3)}
     */
    public String t(String key, Object... args) {
        String pattern = t(key);
        return MessageFormat.format(pattern, args);
    }

    /**
     * Cambia el idioma en tiempo de ejecución.
     * Útil si el usuario quiere cambiar el idioma sin reiniciar.
     */
    public void changeLocale(String lang) {
        loadBundle(Locale.forLanguageTag(lang));
    }

    // -------------------------------------------------------
    // Método estático de conveniencia (evita getInstance() repetitivo)
    // Uso: I18n.msg("menu.main.title")
    // -------------------------------------------------------
    public static String msg(String key) {
        return getInstance().t(key);
    }

    public static String msg(String key, Object... args) {
        return getInstance().t(key, args);
    }
}