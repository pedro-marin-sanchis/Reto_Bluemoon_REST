package com.uguinformatica.bluemoon_api.controller;

import com.uguinformatica.bluemoon_api.mappers.UserRegisterDtoMapper;
import com.uguinformatica.bluemoon_api.models.dao.SilverTypeDAOImpl;
import com.uguinformatica.bluemoon_api.models.dto.UserRegisterDTO;
import com.uguinformatica.bluemoon_api.models.entity.SilverType;
import com.uguinformatica.bluemoon_api.models.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/silver-types")
public class SilverTypeController {

    @Autowired
    private SilverTypeDAOImpl silverTypeService;

    @GetMapping("/" )
    public ResponseEntity<List<?>> showAll() {
        return ResponseEntity.ok(silverTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable long id) {
        SilverType silverType = silverTypeService.findById(id);

        return ResponseEntity.ok(silverType);
    }


    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody SilverType silverType, BindingResult result) {

        if (result.hasFieldErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage()).toList();

            return ResponseEntity.badRequest().body(errors);
        }


        silverTypeService.save(silverType);
        return ResponseEntity.ok(silverType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        SilverType silverType = silverTypeService.findById(id);
        if (silverType == null) {
            return ResponseEntity.notFound().build();
        }
        silverTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody SilverType silverType, BindingResult result) {
        SilverType silverTypeFound = silverTypeService.findById(id);

        if (silverTypeFound == null) {
            return ResponseEntity.notFound().build();
        }

        if (result.hasFieldErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage()).toList();

            return ResponseEntity.badRequest().body(errors);
        }

        silverTypeFound.setId(id);

        silverTypeService.update(silverTypeFound);
        return ResponseEntity.ok(silverType);
    }
}