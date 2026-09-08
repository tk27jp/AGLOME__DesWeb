package com.aglome.ecommerce_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double precio;
    private String color;
    private String medida;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}