import dao.JuegoDAOImpl;
import model.Juego;
import util.DBCreator;
import xml.XmlManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("=== INICIANDO APLICACIÓN DE GESTIÓN DE VIDEOJUEGOS ===\n");

        // Paso 1: Crear BD y tabla
        System.out.println("Creando base de datos y tabla...");
        DBCreator.crearBaseDeDatos();
        JuegoDAOImpl dao = new JuegoDAOImpl();
        dao.crearTabla();
        System.out.println();

        // Paso 2: Insertar juegos iniciales (descomentar solo la primera vez)
        System.out.println("Insertando juegos iniciales...");
        insertarJuegosIniciales(dao);
        System.out.println();

        // Paso 2.5: Generar XML de importación (solo primera vez o cuando quieras recrearlo)
        System.out.println("Generando XML de importación...");
        generarXMLImportacion();
        System.out.println();

        // Paso 3: IMPORTAR desde XML a BD
        System.out.println("IMPORTANDO juegos desde XML a BD...");
        importarXMLaBD(dao);
        System.out.println();

        // Paso 4: EXPORTAR desde BD a XML
        System.out.println("EXPORTANDO juegos desde BD a XML...");
        generarXMLDesdeBD(dao);
        System.out.println();

        // Paso 5: Mostrar todos los juegos en BD
        System.out.println(" Listado final de juegos en la BD:");
        mostrarJuegos(dao);
    }

    /**
     * Inserta 5 juegos iniciales en la base de datos para pruebas.
     *
     * @param dao Objeto DAO para acceder a la base de datos
     * @throws SQLException si hay error en la inserción
     */
    private static void insertarJuegosIniciales(JuegoDAOImpl dao) throws SQLException {
        dao.insertar(new Juego("Final Fantasy VII", "Square", "PlayStation", 1997, "RPG", 29.99));
        dao.insertar(new Juego("Dark Souls", "FromSoftware", "PC", 2011, "Action RPG", 39.99));
        dao.insertar(new Juego("The Witcher 3: Wild Hunt", "CD Projekt Red", "PC", 2015, "RPG", 39.99));
        dao.insertar(new Juego("Bloodborne", "FromSoftware", "PlayStation 4", 2015, "Action RPG", 49.99));
        dao.insertar(new Juego("Elden Ring", "FromSoftware", "PC", 2022, "Action RPG", 59.99));

        System.out.println("5 juegos insertados correctamente.");
    }

    /**
     * Genera el archivo XML de importación con 3 juegos de prueba.
     * Este XML será usado posteriormente para probar la importación a BD.
     */
    private static void generarXMLImportacion() {
        // Crear lista de 3 juegos para importar
        List<Juego> juegosParaImportar = new ArrayList<>();

        juegosParaImportar.add(new Juego("Hollow Knight", "Team Cherry", "PC",
                2017, "Metroidvania", 14.99));
        juegosParaImportar.add(new Juego("Celeste", "Maddy Makes Games", "Nintendo Switch",
                2018, "Platform", 19.99));
        juegosParaImportar.add(new Juego("Hades", "Supergiant Games", "PC",
                2020, "Roguelike", 24.99));

        // Generar el XML
        XmlManager.generarXML(juegosParaImportar, "src/main/juegos_import.xml");
        System.out.println(" XML de importación creado con 3 juegos.");
    }

    /**
     * Importa juegos desde un archivo XML y los inserta en la base de datos (MÉTODO A).
     *
     * @param dao Objeto DAO para acceder a la base de datos
     */
    private static void importarXMLaBD(JuegoDAOImpl dao) {
        String rutaXML = "src/main/juegos_import.xml";
        int insertados = XmlManager.importarXML(rutaXML, dao);
        System.out.println("Total de juegos importados: " + insertados);
    }

    /**
     * Exporta todos los juegos de la base de datos a un archivo XML .
     *
     * @param dao Objeto DAO para acceder a la base de datos
     * @throws SQLException si hay error al obtener los juegos
     */
    private static void generarXMLDesdeBD(JuegoDAOImpl dao) throws SQLException {
        List<Juego> lista = dao.obtenerJuegos();
        XmlManager.generarXML(lista, "src/main/juegos_generados.xml");
        System.out.println(" Total de juegos exportados: " + lista.size());
    }

    /**
     * Muestra todos los juegos almacenados en la base de datos.
     *
     * @param dao Objeto DAO para acceder a la base de datos
     * @throws SQLException si hay error al obtener los juegos
     */
    private static void mostrarJuegos(JuegoDAOImpl dao) throws SQLException {
        List<Juego> juegos = dao.obtenerJuegos();
        juegos.forEach(System.out::println);
        System.out.println("\nTotal de juegos en BD: " + juegos.size());
    }
}