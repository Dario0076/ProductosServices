package com.inventario.ProductosService.controller;

import com.inventario.ProductosService.entity.productos;
import com.inventario.ProductosService.service.productosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class productosController {
    private static final Logger logger = LoggerFactory.getLogger(productosController.class);
    private static final int DEFAULT_UMBRAL_MINIMO = 5;
    private final productosService productosService;
    private final RestTemplate restTemplate;

    public productosController(productosService productosService, RestTemplate restTemplate) {
        this.productosService = productosService;
        this.restTemplate = restTemplate;
    }

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
        // Guardar el producto
        productos savedProducto = productosService.saveProducto(producto);
        
        // Crear stock inicial automáticamente
        try {
            Map<String, Object> stockData = new HashMap<>();
            stockData.put("productoId", savedProducto.getId());
            stockData.put("cantidadActual", savedProducto.getCantidad() != null ? savedProducto.getCantidad() : 0);
            stockData.put("umbralMinimo", DEFAULT_UMBRAL_MINIMO); // Umbral mínimo por defecto
            
            restTemplate.postForObject("http://localhost:8081/stock", stockData, Object.class);
        } catch (Exception e) {
            logger.warn("Error al crear stock inicial para producto {}: {}", savedProducto.getId(), e.getMessage());
        }
        
        return savedProducto;
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

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("service", "ProductosService");
        status.put("timestamp", System.currentTimeMillis());
        return status;
    }
}
