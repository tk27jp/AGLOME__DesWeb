package com.aglome.ecommerce_api.controller;

import com.aglome.ecommerce_api.dto.CategoriaRequestDTO;
import com.aglome.ecommerce_api.entity.Categoria;
import com.aglome.ecommerce_api.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<Categoria> listarTodas() {
        return categoriaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoriaService.obtenerPorId(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Categoria crear(@RequestBody CategoriaRequestDTO categoriaRequest) {
        return categoriaService.crear(categoriaRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id,
            @RequestBody CategoriaRequestDTO categoriaRequest) {
        try {
            return ResponseEntity.ok(categoriaService.actualizar(id, categoriaRequest));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}