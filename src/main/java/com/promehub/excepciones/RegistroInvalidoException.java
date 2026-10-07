package com.promehub.excepciones;

public class RegistroInvalidoException extends Exception{
    public RegistroInvalidoException(String mensaje){
        super(mensaje);
    }
    public RegistroInvalidoException(String mensaje, Throwable causa){
        super(mensaje, causa);
    }
}