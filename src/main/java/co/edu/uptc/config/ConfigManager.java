package co.edu.uptc.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigManager {

    private static final Logger LOGGER = Logger.getLogger(ConfigManager.class.getName());

    private static final String INTERNAL_CONFIG_PATH = "/config/config.properties";

    private static final String EXTERNAL_CONFIG_PATH = "config.properties";

    private static ConfigManager instance;
    private final Properties properties = new Properties();

    private ConfigManager() {
        loadInternalConfig();
        loadExternalConfig();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    private void loadInternalConfig() {
        try (InputStream stream = ConfigManager.class.getResourceAsStream(INTERNAL_CONFIG_PATH)) {
            if (stream == null) {
                throw new IllegalStateException(
                        "Configuración interna no encontrada en: " + INTERNAL_CONFIG_PATH);
            }
            properties.load(stream);
            LOGGER.info("Configuración interna cargada desde el JAR.");
        } catch (IOException e) {
            throw new IllegalStateException("Error leyendo configuración interna.", e);
        }
    }

    private void loadExternalConfig() {
        File externalFile = new File(EXTERNAL_CONFIG_PATH);
        if (!externalFile.exists()) {
            LOGGER.info("No se encontró config externo. Usando solo defaults internos.");
            return;
        }

        Properties external = new Properties();
        try (FileInputStream stream = new FileInputStream(externalFile)) {
            external.load(stream);
            properties.putAll(external);
            LOGGER.info("Configuración externa cargada. Claves sobreescritas: " + external.size());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "No se pudo leer config externo. Usando defaults.", e);
        }
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Clave de configuración no encontrada: " + key);
        }
        return value.trim();
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue).trim();
    }

    public int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            LOGGER.warning("Valor inválido para clave '" + key + "'. Usando default: " + defaultValue);
            return defaultValue;
        }
    }

    public double getDouble(String key, double defaultValue) {
        try {
            return Double.parseDouble(get(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            LOGGER.warning("Valor inválido para clave '" + key + "'. Usando default: " + defaultValue);
            return defaultValue;
        }
    }
}