package dao;

import model.Juego;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JuegoDAOImpl implements JuegoDao{

    /**
     * Inserta un nuevo juego en la base de datos.
     */
    @Override
    public boolean insertar(Juego juego) throws SQLException {
        String sql = "INSERT INTO juegos (titulo, plataforma, desarrollador, anio_lanzamiento, genero, precio) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Rellenamos los parámetros del statement
            stmt.setString(1, juego.getTitulo());
            stmt.setString(2, juego.getPlataforma());
            stmt.setString(3, juego.getDesarrollador());
            stmt.setInt(4, juego.getAnioLanzamiento());
            stmt.setString(5, juego.getGenero());
            stmt.setDouble(6, juego.getPrecio());

            // Ejecutamos el insert
            int filas = stmt.executeUpdate();

            // Si filas > 0, se insertó correctamente
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("error"); e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<Juego> obtenerJuegos() throws SQLException {
        List<Juego>juegos=new ArrayList<>();
        String sql = "SELECT * FROM juegos";
        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                Juego juego= new Juego();
                juego.setId((rs.getInt("id")));
                juego.setTitulo(rs.getString("titulo"));
                juego.setPlataforma(rs.getString("plataforma"));
                juego.setDesarrollador(rs.getString("desarrollador"));
                juego.setAnioLanzamiento(rs.getInt("anio_lanzamiento"));
                juego.setGenero(rs.getString("genero"));
                juego.setPrecio(rs.getDouble("precio"));
                juegos.add(juego);
            }
        }catch (SQLException e){
            System.out.println("Error al obtener el juego " + e.getMessage() );
        }

        return juegos;
    }

    @Override
    public void crearTabla() throws SQLException {
    String sql= "CREATE TABLE IF NOT EXISTS juegos (" +
            "id SERIAL PRIMARY KEY, " +
            "titulo VARCHAR(200) NOT NULL, " +
            "plataforma VARCHAR(100) NOT NULL, " +
            "desarrollador VARCHAR(150), " +
            "anio_lanzamiento INTEGER, " +
            "genero VARCHAR(100), " +
            "precio DECIMAL(10,2))";
    try(Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement()){
        stmt.execute(sql);
        System.out.println("tabla 'juegos' lista (creada o ya existente)");

    } catch (SQLException e) {
        System.out.println("Error al crear tabla juegos. " +e.getMessage());
    }

    }

}
