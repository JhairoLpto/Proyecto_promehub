package com.promehub.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InfoFicheros {
    
    public static void comprobarFichero(Path ruta) throws IOException {
        if (!Files.exists(ruta)) {
            throw new FileNotFoundException("No existe el fichero: " + ruta.toAbsolutePath());
        }
        if (!Files.isRegularFile(ruta)) {
            throw new IOException("La ruta indicada es una carpeta, no un fichero: " + ruta.toAbsolutePath());
        }
    }
    
    public static void mostrarInformacion(Path ruta) {
        System.out.println("=== INFORMACIÓN DEL FICHERO ===");
        System.out.println("Ruta introducida: " + ruta);
        
        if (Files.exists(ruta)) {
            System.out.println("¿Existe?: Sí");
            System.out.println("Ruta absoluta: " + ruta.toAbsolutePath());
            System.out.println("¿Es un fichero regular?: " + (Files.isRegularFile(ruta) ? "Sí" : "No"));
            
            try {
                long tamanoBytes = Files.size(ruta);
                System.out.println("Tamaño: " + tamanoBytes + " bytes (" + String.format("%.2f", tamanoBytes / 1024.0) + " KB)");
            } catch (IOException e) {
                System.err.println("Error al obtener el tamaño del fichero: " + e.getMessage());
            }
        } else {
            System.out.println("¿Existe?: No");
            System.out.println("Ruta absoluta buscada: " + ruta.toAbsolutePath());
        }
    }
}