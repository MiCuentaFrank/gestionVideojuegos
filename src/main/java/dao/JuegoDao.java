package dao;

import model.Juego;

import java.sql.SQLException;
import java.util.List;

public interface JuegoDao {
    //insertar un juego nuevo
    boolean insertar(Juego juego) throws SQLException;

    //obtener lista de Juegos
    List<Juego> obtenerJuegos() throws SQLException;

    //crear tabla juegos si no existe
    void crearTabla() throws SQLException;
}
