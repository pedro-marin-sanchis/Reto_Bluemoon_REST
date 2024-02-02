package com.uguinformatica.bluemoon.apirest.controller;

import com.uguinformatica.bluemoon.apirest.controller.utils.ControllerValidationErrors;
import com.uguinformatica.bluemoon.apirest.models.dao.ProductDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dao.SilverTypeDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.entity.Product;
import com.uguinformatica.bluemoon.apirest.models.entity.SilverType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductDAOImpl productService;

    @GetMapping("")
    public ResponseEntity<List<?>> showAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable long id) {
        Product product = productService.findById(id);

        return ResponseEntity.ok(product);
    }


    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid Product product, BindingResult result) {

        if (result.hasFieldErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Error on field '" + err.getField() + "'. " + err.getDefaultMessage()).toList();

            return ResponseEntity.badRequest().body(errors);
        }


        productService.save(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody Product product, BindingResult result) {
        Product productFound = productService.findById(id);

        if (productFound == null) {
            return ResponseEntity.notFound().build();
        }

        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);

        }

        product.setId(id);

        productService.update(product);
        return ResponseEntity.ok(product);
    }
}