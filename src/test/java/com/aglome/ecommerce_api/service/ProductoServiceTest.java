package com.aglome.ecommerce_api.service;

import com.aglome.ecommerce_api.entity.Producto;
import com.aglome.ecommerce_api.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        productoService = new ProductoService(productoRepository);
    }

    @Test
    void obtenerPorId_cuandoExiste_retornaElProducto() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Polo azul");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.obtenerPorId(1L);

        assertEquals("Polo azul", resultado.getNombre());
        assertEquals(1L, resultado.getId());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaExcepcion() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productoService.obtenerPorId(99L));
    }
}