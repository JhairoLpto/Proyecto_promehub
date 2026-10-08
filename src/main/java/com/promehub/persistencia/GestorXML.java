package com.promehub.persistencia;

import com.promehub.modelo.Catalogo;
import com.promehub.util.InfoFicheros;
import jakarta.xml.bind.*;

import java.io.IOException;
import java.nio.file.Path;

public class GestorXML {

    private final JAXBContext contexto;

    public GestorXML() throws JAXBException {
        this.contexto = JAXBContext.newInstance(Catalogo.class);
    }

    public void exportarXml(Catalogo catalogo, Path ruta) throws JAXBException {
        Marshaller marshaller = contexto.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(catalogo, ruta.toFile());
    }

    public Catalogo importarXml(Path ruta) throws IOException, JAXBException {
        InfoFicheros.comprobarFichero(ruta);
        Unmarshaller unmarshaller = contexto.createUnmarshaller();
        unmarshaller.setEventHandler(evento -> false);
        return (Catalogo) unmarshaller.unmarshal(ruta.toFile());
    }
}
