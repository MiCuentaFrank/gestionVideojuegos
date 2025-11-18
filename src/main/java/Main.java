import dao.JuegoDAOImpl;
import dao.JuegoDao;
import model.Juego;
import util.DBCreator;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBCreator.crearBaseDeDatos();
        JuegoDAOImpl dao=new JuegoDAOImpl();
        dao.crearTabla();
        dao.insertar(new Juego("Final Fantasy VII", "Square", "PlayStation", 1997, "RPG", 29.99));
        dao.insertar(new Juego("Dark Souls", "FromSoftware", "PC", 2011, "Action RPG", 39.99));
        dao.insertar(new Juego("The Witcher 3: Wild Hunt", "CD Projekt Red", "PC", 2015, "RPG", 39.99));
        dao.insertar(new Juego("Bloodborne", "FromSoftware", "PlayStation 4", 2015, "Action RPG", 49.99));
        dao.insertar(new Juego("Elden Ring", "FromSoftware", "PC", 2022, "Action RPG", 59.99));
    }
}
