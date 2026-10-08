# Promehub Data Exchange

Sistema de gestión, persistencia e intercambio de catálogo de videojuegos mediante procesamiento de ficheros CSV y serialización XML con JAXB.

---

## Equipo de Desarrollo

* **Jairo Martín Lucas** — *Team Leader*
* **David López Zurrón** — *Programming Expert*
* **David Domingo Guevara** — *Responsable de Documentación y Q&A*

---

## Descripción del Proyecto

**Promehub Data Exchange** es una herramienta desarrollada en Java para la gestión integral de catálogos de videojuegos. Permite el procesamiento de archivos planos (`.csv`), la transformación de estructuras de datos en memoria, la lectura/escritura mediante JAXB (`.xml`) y la inspección del sistema de archivos en el SO.

### Tecnologías y Librerías Utilizadas
* **Lenguaje:** Java 25 (JDK 25)
* **Arquitectura:** Orientada a objetos y gestión de I/O
* **Librerías XML / JAXB:**
  * `jaxb-api-2.3.1.jar`
  * `jaxb-core-2.3.0.1.jar`
  * `jaxb-impl-2.3.1.jar`
  * `javax.activation-api-1.2.0.jar`

---

## Estructura del Proyecto

```text
Proyecto_promehub/
├── src/
│   └── com/promehub/
│       ├── Main.java
│       ├── modelo/
│       │   ├── Videojuego.java
│       │   └── Catalogo.java
│       └── servicio/
│           ├── GestionCSV.java
│           ├── GestionXML.java
│           └── GestionFicheros.java
├── datos/
│   ├── videojuegos.csv
│   ├── catalogo.xml
│   ├── catalogo_exportado.csv
│   ├── videojuegos_desde_xml.csv
│   └── csv_inventado.csv
└── lib/
    ├── javax.activation-api-1.2.0.jar
    ├── jaxb-api-2.3.1.jar
    ├── jaxb-core-2.3.0.1.jar
    └── jaxb-impl-2.3.1.jar
```

---

## Menú Principal e Interfaz de Usuario

```text
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
========================================
```

---

## Batería de Pruebas Técnicas y Validación (Q&A)

### Test 1: Carga e Importación desde CSV (Opción 1)
* **Objetivo:** Verificar la lectura y parseo correcto del fichero `videojuegos.csv`.
* **Resultado:** Lectura exitosa de 5 registros cargados correctamente en memoria.

### Test 2: Mapeo y Visualización de Atributos Extendidos (Opción 2)
* **Objetivo:** Comprobar la visualización del listado completo con los atributos adicionales (`Stock` y `CodProveedor`).
* **Resultado:** Los objetos mapean y muestran los atributos estándar (`ID`, `Título`, `Plataforma`, `Precio`, `Género`) junto a `Stock` y `CodProveedor`.

### Test 3: Exportación e Integridad XML mediante JAXB (Opción 3)
* **Objetivo:** Comprobar la serialización (Marshalling) a archivo `catalogo.xml`.
* **Resultado:** Generación correcta del archivo XML conteniendo todos los nodos del catálogo.

### Test 4: Manejo de Excepciones y Catálogo Vacío (Opción 3 y Opción 4)
* **Objetivo:** Validar la robustez ante ejecuciones no válidas.
* **Resultado:**
  * Al exportar sin datos previos en memoria: Mensaje controlado `El catálogo está vacío. Carga datos antes de exportar.`.
  * Al pasar un archivo con formato erróneo (CSV en lugar de XML): Captura controlada del error (`[ERROR] No se pudo cargar el XML`).
  * Al intentar cargar ficheros inexistentes: Captura de `FileNotFoundException` personalizada notificando la ruta exacta.

### Test 5: Importación XML mediante Unmarshalling JAXB (Opción 4)
* **Objetivo:** Comprobar la reconstrucción de la colección de objetos Java a partir del archivo `datos/catalogo.xml`.
* **Resultado:** Deserialización exitosa y carga en memoria de la lista de videojuegos.

### Test 6: Exportación de Estructura XML a Fichero CSV (Opción 5)
* **Objetivo:** Exportar los datos recuperados del XML a un nuevo archivo `.csv` formateado (`catalogo_exportado.csv`).
* **Resultado:** Se verifica la creación del fichero conteniendo la cabecera `id, titulo, plataforma, genero, precio, stock, codigoProveedor` y sus correspondientes filas.

### Test 7: Búsqueda Multicriterio (Opción 6)
* **Objetivo:** Verificar las búsquedas por ID y por coincidencia parcial de título.
* **Resultado:** 
  * Búsqueda por ID (`ID: 3`) retorna adecuadamente `Minecraft`.
  * Búsqueda por Título (`GTA V`) retorna `GTA V` con métricas de coincidencias.

### Test 8: Inspección y Metadatos de Ficheros (Opción 7)
* **Objetivo:** Consultar la información del sistema de archivos mediante `java.io.File`.
* **Resultado:** Impresión exitosa de existencia, ruta absoluta en disco, tipo de elemento (fichero regular) y tamaño exacto en bytes/KB (ej: `354 bytes (0,35 KB)` para `csv_inventado.csv`).