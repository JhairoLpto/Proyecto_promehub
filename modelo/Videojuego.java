package modelo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "videojuego")
@XmlAccessorType(XmlAccessType.FIELD)
public class Videojuego {

    @XmlAttribute(name = "id")
    private int id;

    @XmlElement(name = "titulo")
    private String titulo;

    @XmlElement(name = "plataforma")
    private String plataforma;

    @XmlElement(name = "genero")
    private String genero;

    @XmlElement(name = "precio")
    private double precio;

    @XmlElement(name = "stock")
    private int stock;

    // Se excluye del XML usando @XmlTransient según el requerimiento
    @XmlTransient
    private String codigoProveedor;

    // Constructor vacío (Requisito obligatorio de JAXB)
    public Videojuego() {
    }

    // Constructor completo (útil para la lectura del CSV)
    public Videojuego(int id, String titulo, String plataforma, String genero, double precio, int stock, String codigoProveedor) {
        this.id = id;
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.genero = genero;
        this.precio = precio;
        this.stock = stock;
        this.codigoProveedor = codigoProveedor;
    }

    // Getters y Setters
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    @Override
    public String toString() {
        return String.format("ID: %-3d | Título: %-25s | Plataforma: %-10s | Género: %-10s | Precio: %6.2f€ | Stock: %-3d | CodProveedor: %s",
                id, titulo, plataforma, genero, precio, stock, (codigoProveedor != null ? codigoProveedor : "N/A"));
    }
}