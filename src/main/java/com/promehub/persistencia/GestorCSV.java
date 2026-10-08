package com.promehub.persistencia;

import com.promehub.excepciones.RegistroInvalidoException;
import com.promehub.modelo.Videojuego;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GestorCSV {

    private static final int NUM_CAMPOS = 7;
    
    public ResultadoCarga procesarArchivo(Path ruta) throws IOException {
        if (!Files.exists(ruta)) {
            throw new FileNotFoundException("No existe el fichero: " + ruta.toAbsolutePath());
        }
        if (!Files.isRegularFile(ruta)) {
            throw new IOException("La ruta indicada es una carpeta, no un fichero: " + ruta.toAbsolutePath());
        }
        
        List<Videojuego> validos = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        int lineasLeidas = 0;
        
        try (BufferedReader bf = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            String cabecera = bf.readLine();
            if (cabecera == null) {
                return new ResultadoCarga(validos, errores, 0);
            }
            
            int numLinea = 1; // La cabecera fue la línea 1
            String linea;
            
            while ((linea = bf.readLine()) != null) {
                numLinea++; // incrementamos al leer cada nueva línea del archivo (línea 2, 3, etc.)
                
                if (linea.isBlank()) {
                    continue; // Ignoramos líneas en blanco sin alterar la cuenta del número de línea física
                }
                
                lineasLeidas++; // Contamos registros procesados válidos o intentados
                
                try {
                    validos.add(parsearLinea(linea, numLinea));
                } catch (RegistroInvalidoException e) {
                    errores.add(e.getMessage());
                }
            }
        }
        return new ResultadoCarga(validos, errores, lineasLeidas);
    }
    
    private Videojuego parsearLinea(String linea, int numLinea) throws RegistroInvalidoException {
        String[] campos = linea.split(",", -1);
        if (campos.length != NUM_CAMPOS) {
            throw new RegistroInvalidoException("Línea " + numLinea + ": se esperaban "
                + NUM_CAMPOS + " campos y hay " + campos.length);
        }
        for (int i = 0; i < campos.length; i++) {
            campos[i] = campos[i].trim();
        }
        
        // Validación de campos de texto obligatorios
        if (campos[1].isEmpty()) {
            throw new RegistroInvalidoException("Línea " + numLinea + ": el título no puede estar vacío");
        }
        if (campos[2].isEmpty()) {
            throw new RegistroInvalidoException("Línea " + numLinea + ": la plataforma no puede estar vacía");
        }
        if (campos[3].isEmpty()) {
            throw new RegistroInvalidoException("Línea " + numLinea + ": el género no puede estar vacío");
        }
        
        int id = convertirEntero(campos[0], "id", numLinea);
        double precio = convertirDecimal(campos[4], "precio", numLinea);
        int stock = convertirEntero(campos[5], "stock", numLinea);
        
        if (precio < 0) {
            throw new RegistroInvalidoException("Línea " + numLinea + ": el precio no puede ser negativo (" + precio + ")");
        }
        if (stock < 0) {
            throw new RegistroInvalidoException("Línea " + numLinea + ": el stock no puede ser negativo (" + stock + ")");
        }
        
        // Si codigoProveedor viene vacío, se guarda como null
        String codigoProveedor = campos[6].isEmpty() ? null : campos[6];
        
        return new Videojuego(id, campos[1], campos[2], campos[3], precio, stock, codigoProveedor);
    }

    private int convertirEntero(String valor, String campo, int numLinea) throws RegistroInvalidoException {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new RegistroInvalidoException("Línea " + numLinea + ": el " + campo
                    + " '" + valor + "' no es un número entero válido", e);
        }
    }

    private double convertirDecimal(String valor, String campo, int numLinea) throws RegistroInvalidoException {
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            throw new RegistroInvalidoException("Línea " + numLinea + ": el " + campo
                    + " '" + valor + "' no es un número decimal válido", e);
        }
    }
    
    public void exportarArchivo(Path ruta, List<Videojuego> videojuegos) throws IOException {
        List<String> lineas = new ArrayList<>();
        // Cabecera del fichero CSV
        lineas.add("id,titulo,plataforma,genero,precio,stock,codigoProveedor");
        
        // Formatear cada videojuego a una línea CSV
        for (Videojuego v : videojuegos) {
            String codigoProv = (v.getCodigoProveedor() != null) ? v.getCodigoProveedor() : "";
            String linea = String.format(java.util.Locale.US, "%d,%s,%s,%s,%.2f,%d,%s",
                v.getId(),
                v.getTitulo(),
                v.getPlataforma(),
                v.getGenero(),
                v.getPrecio(),
                v.getStock(),
                codigoProv);
            lineas.add(linea);
        }
        
        // Escribir todas las líneas en el fichero especificado
        Files.write(ruta, lineas, StandardCharsets.UTF_8);
    }
}