package co.edu.uptc.i18n;

import co.edu.uptc.config.ConfigManager;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class I18n {

    private static final Logger LOGGER = Logger.getLogger(I18n.class.getName());

    private static final String BUNDLE_BASE = "i18n/messages";

    private static I18n instance;
    private ResourceBundle bundle;

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

    private void loadBundle(Locale locale) {
        try {
            bundle = ResourceBundle.getBundle(BUNDLE_BASE, locale);
            LOGGER.info("Bundle cargado para locale: " + locale);
        } catch (MissingResourceException e) {
            LOGGER.warning("Bundle no encontrado para " + locale + ". Usando español por defecto.");
            bundle = ResourceBundle.getBundle(BUNDLE_BASE, new Locale("es"));
        }
    }

    public String t(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            LOGGER.warning("Clave i18n no encontrada: " + key);
            return "[" + key + "]";  // Fallback visible: alerta al desarrollador
        }
    }

    public String t(String key, Object... args) {
        String pattern = t(key);
        return MessageFormat.format(pattern, args);
    }

    public void changeLocale(String lang) {
        loadBundle(Locale.forLanguageTag(lang));
    }


    public static String msg(String key) {
        return getInstance().t(key);
    }

    public static String msg(String key, Object... args) {
        return getInstance().t(key, args);
    }
}