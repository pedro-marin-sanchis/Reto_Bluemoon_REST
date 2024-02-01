package com.uguinformatica.bluemoon.apirest.controller;

import com.uguinformatica.bluemoon.apirest.controller.utils.ControllerValidationErrors;
import com.uguinformatica.bluemoon.apirest.models.dao.SilverTypeDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dao.TradeDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dao.TradeableDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dao.UserDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dto.TradeCreateEntity;
import com.uguinformatica.bluemoon.apirest.models.dto.TradeableUpdateEntity;
import com.uguinformatica.bluemoon.apirest.models.entity.Trade;
import com.uguinformatica.bluemoon.apirest.models.entity.Tradeable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trades")
public class TradeController {
    @Autowired
    private TradeDAOImpl tradeService;
    @Autowired
    private TradeableDAOImpl tradeableService;
    @Autowired
    private UserDAOImpl userService;
    @Autowired
    private SilverTypeDAOImpl silverTypeService;

    @GetMapping("")
    public ResponseEntity<?> showAll() {
        return ResponseEntity.ok(tradeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable long id) {

        Trade trade = tradeService.findById(id);

        if (trade == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(trade);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid TradeCreateEntity newTrade, BindingResult result) {
        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);
        }

        Trade trade = new Trade();
        trade.setDate(new Date());

        trade.setUser(userService.findById(newTrade.userId));

        System.out.println(newTrade);

        trade.setTradeables(newTrade.tradeables.stream().map((tradeable) -> {
            Tradeable newTradeable = new Tradeable();
            newTradeable.setWeight(tradeable.weight);
            newTradeable.setDescription(tradeable.description);

            System.out.println(tradeable.silverTypeId);
            newTradeable.setSilverType(silverTypeService.findById(tradeable.silverTypeId));

            System.out.println(silverTypeService.findById(tradeable.silverTypeId));


            return tradeableService.save(newTradeable);

        }).collect(Collectors.toSet()));

        tradeService.save(trade);
        return ResponseEntity.ok(newTrade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid TradeableUpdateEntity newTrade, BindingResult result, @PathVariable long id) {

        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);
        }

        Trade trade = tradeService.findById(id);

        if (trade == null) {
            return ResponseEntity.notFound().build();
        }

        trade.setValidated(newTrade.validated);

        tradeService.save(trade);
        return ResponseEntity.ok(trade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Trade trade = tradeService.findById(id);

        if (trade == null) {
            return ResponseEntity.notFound().build();
        }

        tradeService.delete(id);

        return ResponseEntity.ok().build();
    }



}
