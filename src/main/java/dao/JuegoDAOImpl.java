package dao;

import model.Juego;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del patrón DAO para la entidad {@link Juego}.
 * Esta clase contiene los métodos de acceso y manipulación de datos
 * en la tabla 'juegos' dentro de la base de datos PostgreSQL.
 *
 * Se utiliza JDBC junto con {@link DBConnection} para obtener
 * la conexión configurada mediante db.properties.
 */
public class JuegoDAOImpl implements JuegoDao {

    /**
     * Inserta un nuevo juego en la base de datos.
     *
     * @param juego Objeto Juego que contiene los datos a insertar
     * @return true si el juego se insertó correctamente, false en caso contrario
     * @throws SQLException si ocurre un error durante la ejecución del INSERT
     */
    @Override
    public boolean insertar(Juego juego) {
        String sql = "INSERT INTO juegos (titulo, plataforma, desarrollador, anio_lanzamiento, genero, precio) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Rellenar los parámetros del PreparedStatement
            stmt.setString(1, juego.getTitulo());
            stmt.setString(2, juego.getPlataforma());
            stmt.setString(3, juego.getDesarrollador());
            stmt.setInt(4, juego.getAnioLanzamiento());
            stmt.setString(5, juego.getGenero());
            stmt.setDouble(6, juego.getPrecio());

            // Ejecutar INSERT
            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar juego");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene una lista con todos los juegos almacenados en la base de datos.
     *
     * @return Lista de objetos  Juego recuperados de la tabla 'juegos'
     * @throws SQLException si ocurre un error al ejecutar la consulta SELECT
     */
    @Override
    public List<Juego> obtenerJuegos() {
        List<Juego> juegos = new ArrayList<>();
        String sql = "SELECT * FROM juegos";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Recorrer el resultado y construir objetos Juego
            while (rs.next()) {
                Juego juego = new Juego();
                juego.setId(rs.getInt("id"));
                juego.setTitulo(rs.getString("titulo"));
                juego.setPlataforma(rs.getString("plataforma"));
                juego.setDesarrollador(rs.getString("desarrollador"));
                juego.setAnioLanzamiento(rs.getInt("anio_lanzamiento"));
                juego.setGenero(rs.getString("genero"));
                juego.setPrecio(rs.getDouble("precio"));

                juegos.add(juego);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener los juegos: " + e.getMessage());
        }

        return juegos;
    }

    /**
     * Crea la tabla 'juegos' si no existe previamente.
     * La tabla incluye los campos:
     * id, titulo, plataforma, desarrollador, anio_lanzamiento, genero y precio.
     *
     * @throws SQLException si ocurre un error al ejecutar la sentencia CREATE TABLE
     */
    @Override
    public void crearTabla() throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS juegos (" +
                "id SERIAL PRIMARY KEY, " +
                "titulo VARCHAR(200) NOT NULL, " +
                "plataforma VARCHAR(100) NOT NULL, " +
                "desarrollador VARCHAR(150), " +
                "anio_lanzamiento INTEGER, " +
                "genero VARCHAR(100), " +
                "precio DECIMAL(10,2))";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabla 'juegos' lista (creada o ya existente)");

        } catch (SQLException e) {
            System.out.println("Error al crear la tabla juegos: " + e.getMessage());
        }
    }
}
