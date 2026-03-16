package co.edu.uptc.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ConfigManager — Gestor de configuración con estrategia de dos capas.
 *
 * <p>Capa 1 (interna): config.properties dentro del JAR.
 *   Son los valores por defecto. Nunca se modifican en disco.
 *   Si el archivo externo no existe o le falta una clave, se usa este.
 *
 * <p>Capa 2 (externa): config.properties junto al JAR, en el sistema de archivos.
 *   El usuario/cliente puede editarlo. Sus valores sobreescriben los internos EN MEMORIA,
 *   pero NUNCA se toca el archivo interno del JAR.
 *
 * <p>Patrón Singleton: una sola instancia por JVM.
 *
 * <p>Uso:
 * <pre>
 *   int maxName = ConfigManager.getInstance().getInt("person.name.max", 50);
 *   String lang = ConfigManager.getInstance().get("app.language");
 * </pre>
 */
public class ConfigManager {

    private static final Logger LOGGER = Logger.getLogger(ConfigManager.class.getName());

    // Ruta del config INTERNO (dentro del JAR, en classpath)
    private static final String INTERNAL_CONFIG_PATH = "/config/config.properties";

    // Ruta del config EXTERNO (junto al JAR, en el filesystem)
    // Se busca en el directorio de trabajo donde se ejecuta el JAR
    private static final String EXTERNAL_CONFIG_PATH = "config.properties";

    private static ConfigManager instance;
    private final Properties properties = new Properties();

    // -------------------------------------------------------
    // Constructor privado: carga las dos capas al instanciar
    // -------------------------------------------------------
    private ConfigManager() {
        loadInternalConfig();  // Capa 1: defaults (obligatoria)
        loadExternalConfig();  // Capa 2: overrides (opcional)
    }

    // Singleton thread-safe (doble verificación)
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

    // -------------------------------------------------------
    // Capa 1: config interno del JAR
    // -------------------------------------------------------
    private void loadInternalConfig() {
        try (InputStream stream = ConfigManager.class.getResourceAsStream(INTERNAL_CONFIG_PATH)) {
            if (stream == null) {
                // Esto no debería pasar; significa que el JAR está mal empaquetado
                throw new IllegalStateException(
                    "Configuración interna no encontrada en: " + INTERNAL_CONFIG_PATH
                );
            }
            properties.load(stream);
            LOGGER.info("Configuración interna cargada desde el JAR.");
        } catch (IOException e) {
            throw new IllegalStateException("Error leyendo configuración interna.", e);
        }
    }

    // -------------------------------------------------------
    // Capa 2: config externo, opcional, sobreescribe en memoria
    // -------------------------------------------------------
    private void loadExternalConfig() {
        File externalFile = new File(EXTERNAL_CONFIG_PATH);
        if (!externalFile.exists()) {
            LOGGER.info("No se encontró config externo. Usando solo defaults internos.");
            return;
        }

        Properties external = new Properties();
        try (FileInputStream stream = new FileInputStream(externalFile)) {
            external.load(stream);
            // Sobreescribir en memoria (NUNCA se toca el archivo interno)
            properties.putAll(external);
            LOGGER.info("Configuración externa cargada. Claves sobreescritas: " + external.size());
        } catch (IOException e) {
            // Advertencia, no error fatal: si falla el externo, siguen los defaults
            LOGGER.log(Level.WARNING, "No se pudo leer config externo. Usando defaults.", e);
        }
    }

    // -------------------------------------------------------
    // Métodos de acceso tipados
    // -------------------------------------------------------

    /** Obtiene un valor como String. Lanza excepción si la clave no existe. */
    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Clave de configuración no encontrada: " + key);
        }
        return value.trim();
    }

    /** Obtiene un valor como String con fallback si la clave no existe. */
    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue).trim();
    }

    /** Obtiene un valor como int. */
    public int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            LOGGER.warning("Valor inválido para clave '" + key + "'. Usando default: " + defaultValue);
            return defaultValue;
        }
    }

    /** Obtiene un valor como double. */
    public double getDouble(String key, double defaultValue) {
        try {
            return Double.parseDouble(get(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            LOGGER.warning("Valor inválido para clave '" + key + "'. Usando default: " + defaultValue);
            return defaultValue;
        }
    }
}