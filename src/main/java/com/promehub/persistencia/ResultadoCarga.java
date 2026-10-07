package com.promehub.persistencia;

import com.promehub.modelo.Videojuego;

import java.util.List;

public class ResultadoCarga {
    private List<Videojuego> validos;
    private List<String> errores;
    private int lineasLeidas;

    public ResultadoCarga(List<Videojuego> validos, List<String> errores, int lineasLeidas){
        this.validos = validos;
        this.errores = errores;
        this.lineasLeidas = lineasLeidas;
    }

    public List<Videojuego> getValidos() {
        return validos;
    }

    public void setValidos(List<Videojuego> validos) {
        this.validos = validos;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

    public int getLineasLeidas() {
        return lineasLeidas;
    }

    public void setLineasLeidas(int lineasLeidas) {
        this.lineasLeidas = lineasLeidas;
    }

    public int getDescartados(){
        return errores.size();
    }
}