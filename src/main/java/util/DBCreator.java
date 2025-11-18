package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCreator {

    public static void crearBaseDeDatos() {

        String url = Config.get("System.url");
        String user = Config.get("System.user");
        String pass = Config.get("System.password");

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {

            // Desactivar autocommit para evitar errores con CREATE DATABASE
            conn.setAutoCommit(true);

            try (Statement stmt = conn.createStatement()) {

                String sql = "CREATE DATABASE videojuegos_db;";
                stmt.executeUpdate(sql);

                System.out.println("✅ Base de datos 'videojuegos_db' creada con éxito.");
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println(" La base de datos ya existe. Continuamos...");
            } else {
                System.out.println("Error al crear la base de datos:");
                System.out.println(e.getMessage());
            }
        }
    }
}
