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

    public ResultadoCarga leer(Path ruta) throws IOException {
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
            bf.readLine();
            String linea;
            int numLinea = 1;
            while ((linea = bf.readLine()) != null) {
                numLinea++;
                if (linea.isBlank()) {
                    continue;
                }
                lineasLeidas++;
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
        int id = convertirEntero(campos[0], "id", numLinea);
        double precio = convertirDecimal(campos[4], "precio", numLinea);
        int stock = convertirEntero(campos[5], "stock", numLinea);
        return new Videojuego(id, campos[1], campos[2], campos[3], precio, stock, campos[6]);
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
}