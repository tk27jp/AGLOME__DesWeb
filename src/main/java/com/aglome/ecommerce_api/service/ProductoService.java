package com.aglome.ecommerce_api.service;

import com.aglome.ecommerce_api.entity.Producto;
import com.aglome.ecommerce_api.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con id: " + id));
    }

    public Producto crear(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new NoSuchElementException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }

    public Producto actualizar(Long id, Producto datosActualizados) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con id: " + id));

        producto.setNombre(datosActualizados.getNombre());
        producto.setPrecio(datosActualizados.getPrecio());
        producto.setColor(datosActualizados.getColor());
        producto.setMedida(datosActualizados.getMedida());
        producto.setCategoria(datosActualizados.getCategoria());

        return productoRepository.save(producto);
    }
}