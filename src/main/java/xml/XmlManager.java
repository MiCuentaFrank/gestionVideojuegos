package xml;

import dao.JuegoDAOImpl;
import model.Juego;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

/**
 * Clase de utilidad encargada de gestionar operaciones relacionadas con XML
 * utilizando el API DOM (Document Object Model).
 * <p>
 * Esta clase permite:
 * <ul>
 *     <li>Generar un archivo XML a partir de una lista de objetos {@link Juego}</li>
 *     <li>Importar un archivo XML, leer su contenido y almacenarlo en la base de datos</li>
 * </ul>
 * <p>
 * Se utiliza en combinación con {@link JuegoDAOImpl} para la inserción de datos.
 */
public class XmlManager {

    /**
     * Genera un archivo XML a partir de una lista de juegos.
     * Cada objeto {@link Juego} se convierte en un nodo dentro del XML siguiendo
     * la estructura:
     * @param juegos     Lista de juegos a volcar en el XML
     * @param rutaSalida Ruta donde se guardará el archivo generado
     */
    public static void generarXML(List<Juego> juegos, String rutaSalida) {
        try {
            // Crear DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Crear el documento vacío
            Document doc = builder.newDocument();

            // Elemento raíz <juegos>
            Element root = doc.createElement("juegos");
            doc.appendChild(root);

            // Recorrer la lista de juegos y crear nodos
            for (Juego j : juegos) {
                Element juego = doc.createElement("juego");

                Element titulo = doc.createElement("titulo");
                titulo.appendChild(doc.createTextNode(j.getTitulo()));
                juego.appendChild(titulo);

                Element desarrollador = doc.createElement("desarrollador");
                desarrollador.appendChild(doc.createTextNode(j.getDesarrollador()));
                juego.appendChild(desarrollador);

                Element plataforma = doc.createElement("plataforma");
                plataforma.appendChild(doc.createTextNode(j.getPlataforma()));
                juego.appendChild(plataforma);

                Element anio = doc.createElement("anio_lanzamiento");
                anio.appendChild(doc.createTextNode(String.valueOf(j.getAnioLanzamiento())));
                juego.appendChild(anio);

                Element genero = doc.createElement("genero");
                genero.appendChild(doc.createTextNode(j.getGenero()));
                juego.appendChild(genero);

                Element precio = doc.createElement("precio");
                precio.appendChild(doc.createTextNode(String.valueOf(j.getPrecio())));
                juego.appendChild(precio);

                root.appendChild(juego);
            }

            // Preparar transformador para guardar el XML
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // Crear el archivo físico
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(rutaSalida));

            transformer.transform(source, result);

            System.out.println("XML creado en la carpeta: " + rutaSalida);

        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("Error al crear el XML: " + e.getMessage());
        }
    }

    /**
     * Importa juegos desde un archivo XML y los inserta en la base de datos.
     * El método:
     * Lee el archivo XML usando DOM
     * Extrae los datos de cada nodo juego
     * Crea objetos  Juego
     * Inserta cada juego en la base de datos usando JuegoDAOImpl
     * @param rutaXML Ruta del archivo XML a importar
     * @param dao     Objeto DAO que permite insertar los juegos en la BD
     * @return Número total de juegos importados e insertados correctamente
     */
    public static int importarXML(String rutaXML, JuegoDAOImpl dao) {
        int insertados = 0;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(new File(rutaXML));
            doc.getDocumentElement().normalize();

            // OJO: en minúsculas
            NodeList listaJuegos = doc.getElementsByTagName("juego");

            for (int i = 0; i < listaJuegos.getLength(); i++) {
                Node nodo = listaJuegos.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;

                    // OJO: todas en minúsculas
                    String titulo = elemento.getElementsByTagName("titulo").item(0).getTextContent();
                    String desarrollador = elemento.getElementsByTagName("desarrollador").item(0).getTextContent();
                    String plataforma = elemento.getElementsByTagName("plataforma").item(0).getTextContent();
                    int anio = Integer.parseInt(elemento.getElementsByTagName("anio_lanzamiento").item(0).getTextContent());
                    String genero = elemento.getElementsByTagName("genero").item(0).getTextContent();
                    double precio = Double.parseDouble(elemento.getElementsByTagName("precio").item(0).getTextContent());

                    Juego juego = new Juego(titulo, desarrollador, plataforma, anio, genero, precio);

                    if (dao.insertar(juego)) {
                        insertados++;
                    }
                }
            }

            System.out.println("Juegos importados desde XML: " + insertados);

        } catch (Exception e) {
            System.err.println("Error al importar XML: " + e.getMessage());
        }

        return insertados;
    }

}
