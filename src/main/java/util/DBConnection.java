package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase utilitaria para gestionar la conexión a la base de datos PostgreSQL.
 * Lee la configuración desde el archivo database.properties.
 */
public class DBConnection {
    private static Properties prop = new Properties();

    //bloque estatico que permite cargar el archivo db.properties
    static{
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")){
            if(input == null){
                throw new RuntimeException("Error al obtener la informacion de datos");
            }
            prop.load(input);
        }catch(Exception e){
            System.out.println("Error al obtener la informacion de datos " + e.getMessage());
        }
    }
    /**
     * Obtiene una conexión a la base de datos.
     *
     * @return Connection objeto de conexión a PostgreSQL
     * @throws SQLException si hay un error al conectar
     */
    public static Connection getConnection() throws SQLException {
        String url = prop.getProperty("db.url");
        String user = prop.getProperty("db.user");
        String password = prop.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }
    /**
     * Cierra una conexión de forma segura.
     *
     * @param connection la conexión a cerrar
     */
    public static void cerrarConexion(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}