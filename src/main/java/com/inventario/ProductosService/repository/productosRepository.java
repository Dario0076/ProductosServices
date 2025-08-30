package com.inventario.ProductosService.repository;

import com.inventario.ProductosService.entity.productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface productosRepository extends JpaRepository<productos, Long> {
    List<productos> findByActivoTrue();
}
