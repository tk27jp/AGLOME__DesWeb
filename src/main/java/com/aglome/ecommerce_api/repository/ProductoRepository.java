package com.aglome.ecommerce_api.repository;

import com.aglome.ecommerce_api.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByColorOrMedida(String color, String medida);
    List<Producto> findByCategoriaId(Long categoriaId);
}