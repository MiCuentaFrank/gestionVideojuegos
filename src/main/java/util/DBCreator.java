package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase encargada de crear la base de datos principal del sistema.
 * Lee la URL, usuario y contraseña desde el archivo db.properties
 * utilizando la clase {@link Config}.
 *
 * Esta clase se ejecuta una sola vez al inicio de la aplicación
 * para asegurarse de que la base de datos existe antes de crear
 * las tablas o realizar operaciones JDBC.
 */
public class DBCreator {

    /** Nombre de la base de datos que queremos crear */
    private static final String DB_NAME = "videojuegos_db";

    /**
     * Crea la base de datos si no existe. Si ya existe, muestra un mensaje informativo.
     *
     * @return true si la BD se creó, false si ya existía
     */
    public static boolean crearBaseDeDatos() {

        String url = Config.get("System.url");
        String user = Config.get("System.user");
        String pass = Config.get("System.password");

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE DATABASE " + DB_NAME;
            stmt.executeUpdate(sql);

            System.out.println("✔Base de datos '" + DB_NAME + "' creada con éxito.");
            return true;

        } catch (SQLException e) {

            if (e.getMessage().contains("already exists")) {
                System.out.println("ℹ La base de datos '" + DB_NAME + "' ya existe. Continuamos...");
                return false;

            } else {
                System.out.println(" Error al crear la base de datos:");
                System.out.println(e.getMessage());
                return false;
            }
        }
    }
}
