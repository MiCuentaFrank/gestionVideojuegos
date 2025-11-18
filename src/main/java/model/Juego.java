package model;

public class Juego {
    private int id;
    private String titulo;
    private String plataforma;
    private String desarrollador;
    private int anioLanzamiento;
    private String genero;
    private double precio;

    public Juego() {
    }

    public Juego(String titulo, String desarrollador, String plataforma, int anioLanzamiento, String genero, double precio) {
        this.titulo = titulo;
        this.desarrollador = desarrollador;
        this.plataforma = plataforma;
        this.anioLanzamiento = anioLanzamiento;
        this.genero = genero;
        this.precio = precio;
    }

    public Juego(int id, String titulo, String plataforma, int anioLanzamiento, String desarrollador, String genero, double precio) {
        this.id = id;
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.anioLanzamiento = anioLanzamiento;
        this.desarrollador = desarrollador;
        this.genero = genero;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public int getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(int anioLanzamiento) {
        this.anioLanzamiento = anioLanzamiento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Juego{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", plataforma='" + plataforma + '\'' +
                ", desarrollador='" + desarrollador + '\'' +
                ", anioLanzamiento=" + anioLanzamiento +
                ", genero='" + genero + '\'' +
                ", precio=" + precio +
                '}';
    }
}
