package com.inventario.ProductosService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "productos")
public class productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer cantidad;
    private Boolean activo = true;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

}
