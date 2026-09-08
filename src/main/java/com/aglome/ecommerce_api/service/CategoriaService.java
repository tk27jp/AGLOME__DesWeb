package com.aglome.ecommerce_api.service;

import com.aglome.ecommerce_api.dto.CategoriaRequestDTO;
import com.aglome.ecommerce_api.entity.Categoria;
import com.aglome.ecommerce_api.entity.Producto;
import com.aglome.ecommerce_api.repository.CategoriaRepository;
import com.aglome.ecommerce_api.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Categoria no encontrada con id: " + id));
    }

    public Categoria crear(CategoriaRequestDTO request) {
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        List<Long> productoIds = request.getProductoIds();
        if (productoIds != null && !productoIds.isEmpty()) {
            List<Producto> productos = productoRepository.findAllById(productoIds);

            if (productos.size() != productoIds.size()) {
                throw new NoSuchElementException("Uno o más productos no fueron encontrados");
            }

            for (Producto producto : productos) {
                producto.setCategoria(categoriaGuardada);
            }
            productoRepository.saveAll(productos);
            categoriaGuardada.setProductos(productos);
        } else {
            categoriaGuardada.setProductos(Collections.emptyList());
        }

        return categoriaGuardada;
    }

    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new NoSuchElementException("Categoria no encontrada con id: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    public Categoria actualizar(Long id, CategoriaRequestDTO request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Categoria no encontrada con id: " + id));

        categoria.setNombre(request.getNombre());

        List<Long> nuevosIds = request.getProductoIds();
        if (nuevosIds != null) {
            List<Producto> productosActuales = productoRepository.findByCategoriaId(id);
            for (Producto p : productosActuales) {
                if (!nuevosIds.contains(p.getId())) {
                    p.setCategoria(null);
                }
            }
            productoRepository.saveAll(productosActuales);

            List<Producto> nuevosProductos = productoRepository.findAllById(nuevosIds);
            if (nuevosProductos.size() != nuevosIds.size()) {
                throw new NoSuchElementException("Uno o más productos no fueron encontrados");
            }
            for (Producto p : nuevosProductos) {
                p.setCategoria(categoria);
            }
            productoRepository.saveAll(nuevosProductos);
        }

        return categoriaRepository.save(categoria);
    }
}