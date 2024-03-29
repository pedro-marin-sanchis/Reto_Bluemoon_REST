package com.uguinformatica.bluemoon.apirest.controller;

import com.uguinformatica.bluemoon.apirest.controller.utils.ControllerValidationErrors;
import com.uguinformatica.bluemoon.apirest.models.dao.SilverTypeDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.entity.SilverType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/silver-types")
public class SilverTypeController {

    @Autowired
    private SilverTypeDAOImpl silverTypeService;

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<?>> showAll() {
        return ResponseEntity.ok(silverTypeService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> show(@PathVariable long id) {
        SilverType silverType = silverTypeService.findById(id);

        return ResponseEntity.ok(silverType);
    }


    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> create(@RequestBody @Valid SilverType silverType, BindingResult result) {

        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);

        }


        silverTypeService.save(silverType);
        return ResponseEntity.ok(silverType);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable long id) {
        SilverType silverType = silverTypeService.findById(id);
        if (silverType == null) {
            return ResponseEntity.notFound().build();
        }
        silverTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid SilverType silverType, BindingResult result) {
        SilverType silverTypeFound = silverTypeService.findById(id);

        if (silverTypeFound == null) {
            return ResponseEntity.notFound().build();
        }

        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);
        }

        silverTypeFound.setId(id);
        silverTypeFound.setName(silverType.getName());
        silverTypeFound.setCurrentPrice(silverType.getCurrentPrice());

        silverTypeService.update(silverTypeFound);
        return ResponseEntity.ok(silverType);
    }


}