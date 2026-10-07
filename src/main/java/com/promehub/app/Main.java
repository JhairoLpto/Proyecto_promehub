package com.promehub.app;

import com.promehub.servicio.GestorCatalogo;
import com.promehub.util.MyScanner;
import jakarta.xml.bind.JAXBException;

public class Main {
    private static final MyScanner sc = new MyScanner();
    
    public static void main(String[] args) {
        
        try {
            GestorCatalogo gc = new GestorCatalogo();
            
            int entradaUser;
            
            do {
                entradaUser = sc.pedirNumero("""
                    ========================================
                             PROMEHUB DATA EXCHANGE
                    ========================================
                    1. Cargar catálogo desde CSV
                    2. Mostrar catálogo
                    3. Exportar catálogo a XML
                    4. Cargar catálogo desde XML
                    5. Exportar catálogo a CSV
                    6. Buscar videojuego
                    7. Información de ficheros
                    0. Salir
                    """);
                
                switch (entradaUser) {
                    case 1:
                        try {
                            gc.cargarDesdeCSV("datos/videojuegos.csv");
                            System.out.println("Catálogo cargado correctamente desde el CSV.");
                        } catch (Exception e) {
                            System.err.println("Error al cargar el CSV: " + e.getMessage());
                        }
                        break;
                    
                    case 2:
                        // Mostrar catálogo
                        if (gc.getCatalogo().getVideojuegos().isEmpty()) {
                            System.out.println("El catálogo está vacío. Carga datos primero (Opción 1 o 4).");
                        } else {
                            System.out.println("=== LISTADO DE VIDEOJUEGOS ===");
                            gc.getCatalogo().getVideojuegos().forEach(v -> System.out.println("ID: " + v.getId() + " | Título: " + v.getTitulo() + " | Plataforma: " + v.getPlataforma() + " | Precio: " + v.getPrecio() + "€"));
                        }
                        break;
                    
                    case 3:
                        try {
                            gc.exportarXml("datos/catalogo.xml");
                        } catch (Exception e) {
                            System.err.println("Error al exportar a XML: " + e.getMessage());
                        }
                        break;
                    
                    case 4:
                        try {
                            gc.cargarDesdeXml("datos/catalogo.xml");
                        } catch (Exception e) {
                            System.err.println("Error al cargar el XML: " + e.getMessage());
                        }
                        break;
                    case 5:
                        break;
                    case 6:
                        int tipoVarVideojuego = sc.pedirNumero("""
                            ========================================
                            ELIGE BÚSQUEDA POR ID O TÍTULO
                            ========================================
                            1. Buscar por id
                            2. Buscar por título
                            """);
                        if (tipoVarVideojuego == 1) {
                        
                        } else if (tipoVarVideojuego == 2) {
                        
                        } else {
                            System.out.println("Escoja una opción válida");
                        }
                        break;
                    case 7:
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Escoja una opción válida");
                        break;
                }
            } while (entradaUser != 0);
        } catch (JAXBException e) {
            System.out.println("Error cargando e gestor del catálogo: " + e.getMessage());
        }
    }
}
