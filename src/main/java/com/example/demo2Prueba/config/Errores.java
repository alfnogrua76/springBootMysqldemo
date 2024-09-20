package com.example.demo2Prueba.config;

import lombok.Getter;

@Getter
public enum Errores {
    INTERNAL_ERROR("Hubo un error en el servidor, intente mas tarde"),
    NOT_FOUND("No se encontro el registro");

    private String descripcion;

    Errores(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
