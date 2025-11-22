package util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Clase de utilidad encargada de cargar y gestionar las propiedades
 * definidas en el archivo db.properties
 * Esta clase se utiliza para leer parámetros de configuración
 * El archivo db.properties debe estar ubicado en:
 *     src/main/resources/db.properties
 * El contenido se carga una única vez gracias al bloque estático,
 * lo que mejora el rendimiento evitando leer el fichero repetidamente.
 */
public class Config {

    /** Objeto Properties que almacenará las claves y valores cargados del archivo. */
    private static final Properties prop = new Properties();

    // Bloque estático que se ejecuta una única vez al cargar la clase
    static {
        try (
                InputStream input = Config.class
                        .getClassLoader()
                        .getResourceAsStream("db.properties")
        ) {
            // Cargar el archivo de propiedades en memoria
            prop.load(input);

        } catch (Exception ex) {
            System.out.println("Error al cargar db.properties");
        }
    }

    /**
     * Obtiene el valor asociado a una clave del archivo de propiedades.
     *
     * @param key Nombre de la propiedad que se quiere consultar
     * @return El valor correspondiente a la clave, o null si no existe
     */
    public static String get(String key) {
        return prop.getProperty(key);
    }
}
