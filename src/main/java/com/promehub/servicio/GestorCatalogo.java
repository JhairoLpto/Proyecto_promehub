package com.promehub.servicio;

import com.promehub.modelo.Catalogo;
import com.promehub.modelo.Videojuego;
import com.promehub.persistencia.GestorCSV;
import com.promehub.persistencia.GestorXML;
import com.promehub.persistencia.ResultadoCarga;
import jakarta.xml.bind.JAXBException;

import java.nio.file.Path;
import java.nio.file.Paths;
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
    
    public Catalogo getCatalogo() {
        return catalogo;
    }
}
