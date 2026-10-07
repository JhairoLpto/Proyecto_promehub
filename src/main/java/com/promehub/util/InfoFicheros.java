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
}
