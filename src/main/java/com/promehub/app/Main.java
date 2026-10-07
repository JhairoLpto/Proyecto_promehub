package com.promehub.app;

import com.promehub.util.MyScanner;

public class Main {
    private static MyScanner sc = new MyScanner();

    public static void main(String[] args) {

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

                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
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

                    }else{
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
    }

    public static void menu() {
        System.out.println("========================================");
        System.out.println("PROMEHUB DATA EXCHANGE");
        System.out.println("========================================");
        System.out.println("1. Cargar catálogo desde CSV");
        System.out.println("2. Mostrar catálogo");
        System.out.println("3. Exportar catálogo a XML");
        System.out.println("4. Cargar catálogo desde XML");
        System.out.println("5. Exportar catálogo a CSV");
        System.out.println("6. Buscar videojuego");
        System.out.println("7. Información de ficheros");
        System.out.println("0. Salir");
    }
}
