package com.aglome.ecommerce_api.dto;

import java.util.List;

public class CategoriaRequestDTO {

    private String nombre;
    private List<Long> productoIds;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Long> getProductoIds() {
        return productoIds;
    }

    public void setProductoIds(List<Long> productoIds) {
        this.productoIds = productoIds;
    }
}