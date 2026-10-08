package com.promehub.app;

import com.promehub.modelo.Videojuego;
import com.promehub.servicio.GestorCatalogo;
import com.promehub.util.InfoFicheros;
import com.promehub.util.MyScanner;
import jakarta.xml.bind.JAXBException;

import java.nio.file.Paths;
import java.util.List;

public class Main {
    private static final MyScanner sc = new MyScanner();
    
    public static void main(String[] args) {
        GestorCatalogo gc;
        
        try {
            gc = new GestorCatalogo();
        } catch (JAXBException e) {
            System.err.println("Error crítico al inicializar el gestor de catálogo (JAXB): " + e.getMessage());
            return;
        }
        
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
                    String rutaCsv = sc.pideTexto("Introduce la ruta del fichero CSV a cargar (ej: datos/videojuegos.csv):");
                    try {
                        gc.cargarDesdeCSV(rutaCsv);
                        System.out.println("\nCatálogo cargado correctamente desde el CSV.");
                    } catch (Exception e) {
                        System.err.println("\n[ERROR] No se pudo cargar el CSV: " + e.getMessage());
                    }
                    break;
                
                case 2:
                    if (gc.getCatalogo().getVideojuegos().isEmpty()) {
                        System.out.println("\nEl catálogo está vacío. Carga datos primero (Opción 1 o 4).");
                    } else {
                        System.out.println("\n======================================== LISTADO DE VIDEOJUEGOS ========================================");
                        for (Videojuego v : gc.getCatalogo().getVideojuegos()) {
                            System.out.println(v);
                        }
                        System.out.println("Total de videojuegos en memoria: " + gc.getCatalogo().getVideojuegos().size());
                    }
                    break;
                
                case 3:
                    if (gc.getCatalogo().getVideojuegos().isEmpty()) {
                        System.out.println("\nEl catálogo está vacío. Carga datos antes de exportar.");
                        break;
                    }
                    String rutaXmlExport = sc.pideTexto("Introduce la ruta de destino para el XML (ej: datos/catalogo.xml):");
                    try {
                        gc.exportarXml(rutaXmlExport);
                        System.out.println("\nCatálogo exportado con éxito a XML.");
                    } catch (Exception e) {
                        System.err.println("\n[ERROR] No se pudo exportar a XML: " + e.getMessage());
                    }
                    break;
                
                case 4:
                    String rutaXmlImport = sc.pideTexto("Introduce la ruta del fichero XML a cargar (ej: datos/catalogo.xml):");
                    try {
                        gc.cargarDesdeXml(rutaXmlImport);
                        System.out.println("\nCatálogo cargado con éxito desde XML.");
                    } catch (Exception e) {
                        System.err.println("\n[ERROR] No se pudo cargar el XML: " + e.getMessage());
                    }
                    break;
                
                case 5:
                    if (gc.getCatalogo().getVideojuegos().isEmpty()) {
                        System.out.println("\nEl catálogo está vacío. Carga datos antes de exportar.");
                        break;
                    }
                    String rutaCsvExport = sc.pideTexto("Introduce la ruta de destino para el CSV (ej: datos/catalogo_exportado.csv):");
                    try {
                        gc.exportarCsv(rutaCsvExport);
                        System.out.println("\nCatálogo exportado con éxito a CSV.");
                    } catch (Exception e) {
                        System.err.println("\n[ERROR] No se pudo exportar a CSV: " + e.getMessage());
                    }
                    break;
                
                case 6:
                    if (gc.getCatalogo().getVideojuegos().isEmpty()) {
                        System.out.println("\nEl catálogo está vacío. Carga datos primero (Opción 1 o 4).");
                        break;
                    }
                    
                    int tipoVarVideojuego = sc.pedirNumero("""
                        ========================================
                        ELIGE BÚSQUEDA POR ID O TÍTULO
                        ========================================
                        1. Buscar por id
                        2. Buscar por título
                        """);
                    
                    if (tipoVarVideojuego == 1) {
                        int idBuscado = sc.pedirNumero("Introduce el ID del videojuego:");
                        Videojuego vEncontrado = gc.buscarPorId(idBuscado);
                        
                        if (vEncontrado != null) {
                            System.out.println("\nVideojuego encontrado:");
                            System.out.println(vEncontrado);
                        } else {
                            System.out.println("\nNo se encontró ningún videojuego con ID: " + idBuscado);
                        }
                        
                    } else if (tipoVarVideojuego == 2) {
                        String tituloBuscado = sc.pideTexto("Introduce el título o parte del título a buscar:");
                        List<Videojuego> resultados = gc.buscarPorTitulo(tituloBuscado);
                        
                        if (resultados.isEmpty()) {
                            System.out.println("\nNo se encontraron videojuegos que coincidan con '" + tituloBuscado + "'.");
                        } else {
                            System.out.println("\n=================== RESULTADOS DE LA BÚSQUEDA ===================");
                            for (Videojuego v : resultados) {
                                System.out.println(v);
                            }
                            System.out.println("Total de coincidencias encontradas: " + resultados.size());
                        }
                    } else {
                        System.out.println("\nEscoja una opción válida (1 o 2).");
                    }
                    break;
                
                case 7:
                    String rutaInfo = sc.pideTexto("Introduce la ruta del fichero a consultar (ej: datos/videojuegos.csv):");
                    System.out.println();
                    InfoFicheros.mostrarInformacion(Paths.get(rutaInfo));
                    break;
                
                case 0:
                    System.out.println("\nSaliendo del programa...");
                    break;
                
                default:
                    System.out.println("\nOpción no válida. Elija un número entre 0 y 7.");
                    break;
            }
        } while (entradaUser != 0);
        
        sc.cerrar();
    }
}