package com.aglome.ecommerce_api.controller;

import com.aglome.ecommerce_api.entity.Producto;
import com.aglome.ecommerce_api.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @Test
    void obtenerPorId_cuandoExiste_retornaOk() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Polo azul");

        when(productoService.obtenerPorId(1L)).thenReturn(producto);

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Polo azul"));
    }

    @Test
    void obtenerPorId_cuandoNoExiste_retorna404() throws Exception {
        when(productoService.obtenerPorId(99L))
                .thenThrow(new NoSuchElementException("Producto no encontrado"));

        mockMvc.perform(get("/api/productos/99"))
                .andExpect(status().isNotFound());
    }
}