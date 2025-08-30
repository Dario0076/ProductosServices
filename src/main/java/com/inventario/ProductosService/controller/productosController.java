package com.inventario.ProductosService.controller;

import com.inventario.ProductosService.entity.productos;
import com.inventario.ProductosService.service.productosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class productosController {
    @Autowired
    private productosService productosService;

    @GetMapping
    public List<productos> getAllProductos() {
        return productosService.getAllProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<productos> getProductoById(@PathVariable Long id) {
        Optional<productos> producto = productosService.getProductoById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public productos createProducto(@RequestBody productos producto) {
        return productosService.saveProducto(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<productos> updateProducto(@PathVariable Long id, @RequestBody productos producto) {
        if (!productosService.getProductoById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productos updated = productosService.updateProducto(id, producto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        if (!productosService.getProductoById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productosService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}
