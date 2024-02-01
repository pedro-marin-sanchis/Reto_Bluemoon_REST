package com.uguinformatica.bluemoon.apirest.controller;

import com.uguinformatica.bluemoon.apirest.controller.utils.ControllerValidationErrors;
import com.uguinformatica.bluemoon.apirest.models.dao.TradeDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.entity.Trade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trade")
public class TradeController {
    @Autowired
    private TradeDAOImpl tradeService;

    @GetMapping("")
    public ResponseEntity<?> showAll() {
        return ResponseEntity.ok(tradeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(long id) {
        return ResponseEntity.ok(tradeService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid Trade trade, BindingResult result) {
        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);
        }

        tradeService.save(trade);
        return ResponseEntity.ok(trade);
    }

}
