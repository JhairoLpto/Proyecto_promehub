package com.promehub.servicio;

import com.promehub.modelo.Catalogo;
import com.promehub.modelo.Videojuego;
import com.promehub.persistencia.GestorCSV;
import com.promehub.persistencia.GestorXML;
import com.promehub.persistencia.ResultadoCarga;
import jakarta.xml.bind.JAXBException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestorCatalogo {
    
    private Catalogo catalogo;
    private final GestorCSV gestorCSV;
    private final GestorXML gestorXML;
    
    public GestorCatalogo() throws JAXBException {
        this.catalogo = new Catalogo();
        this.gestorCSV = new GestorCSV();
        this.gestorXML = new GestorXML();
    }
    
    // Opciones del menú
    public void cargarDesdeCSV(String rutaFichero) throws Exception {
        Path ruta = Paths.get(rutaFichero);
        ResultadoCarga resultado = gestorCSV.procesarArchivo(ruta);
        this.catalogo = new Catalogo(resultado.getValidos());
        
        System.out.println("Líneas leídas: " + resultado.getLineasLeidas());
        System.out.println("Juegos cargados: " + resultado.getValidos().size());
        if (!resultado.getErrores().isEmpty()) {
            System.out.println("Errores encontrados: " + resultado.getErrores().size());
        }
    }
    
    public void exportarXml(String rutaFichero) throws Exception {
        Path ruta = Paths.get(rutaFichero);
        gestorXML.exportarXml(this.catalogo, ruta);
        System.out.println("Catálogo exportado con éxito a XML.");
    }
    
    public void cargarDesdeXml(String rutaFichero) throws Exception {
        Path ruta = Paths.get(rutaFichero);
        this.catalogo = gestorXML.importarXml(ruta);
        System.out.println("Catálogo cargado con éxito desde XML.");
    }
    
    public void exportarCsv(String rutaFichero) throws Exception {
        if (this.catalogo.getVideojuegos().isEmpty()) {
            throw new Exception("El catálogo está vacío. No hay datos para exportar.");
        }
        Path ruta = Paths.get(rutaFichero);
        gestorCSV.exportarArchivo(ruta, this.catalogo.getVideojuegos());
        System.out.println("Catálogo exportado con éxito a CSV.");
    }
    
    public Videojuego buscarPorId(int id) {
        for (Videojuego v : catalogo.getVideojuegos()) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }
    
    public List<Videojuego> buscarPorTitulo(String titulo) {
        List <Videojuego> coincidencias = new ArrayList <>();
        String tituloBusqueda = titulo.toLowerCase().trim();
        
        for (Videojuego v : catalogo.getVideojuegos()) {
            if (v.getTitulo().toLowerCase().contains(tituloBusqueda)) {
                coincidencias.add(v);
            }
        }
        return coincidencias;
    }
    
    public Catalogo getCatalogo() {
        return catalogo;
    }
}
