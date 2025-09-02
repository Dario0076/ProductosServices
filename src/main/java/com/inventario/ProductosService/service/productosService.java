package com.inventario.ProductosService.service;

import com.inventario.ProductosService.entity.productos;
import com.inventario.ProductosService.repository.productosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class productosService {
    private final productosRepository productosRepository;

    public productosService(productosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    public List<productos> getAllProductos() {
        return productosRepository.findByActivoTrue();
    }

    public Optional<productos> getProductoById(Long id) {
        return productosRepository.findById(id);
    }

    public productos saveProducto(productos producto) {
        return productosRepository.save(producto);
    }

    public productos updateProducto(Long id, productos producto) {
        producto.setId(id);
        return productosRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        Optional<productos> productoOpt = productosRepository.findById(id);
        if (productoOpt.isPresent()) {
            productos producto = productoOpt.get();
            producto.setActivo(false);
            productosRepository.save(producto);
        }
    }
}
