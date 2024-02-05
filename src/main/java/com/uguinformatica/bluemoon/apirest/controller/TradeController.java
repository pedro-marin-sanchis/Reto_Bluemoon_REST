package com.uguinformatica.bluemoon.apirest.controller;

import com.uguinformatica.bluemoon.apirest.controller.utils.ControllerValidationErrors;
import com.uguinformatica.bluemoon.apirest.models.dao.SilverTypeDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dao.TradeDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dao.TradeableDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dao.UserDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dto.TradeCreateEntity;
import com.uguinformatica.bluemoon.apirest.models.dto.TradeableCreateEntity;
import com.uguinformatica.bluemoon.apirest.models.dto.TradeUpdateEntity;
import com.uguinformatica.bluemoon.apirest.models.dto.TradeableUpdateEntity;
import com.uguinformatica.bluemoon.apirest.models.entity.Trade;
import com.uguinformatica.bluemoon.apirest.models.entity.Tradeable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> showAll() {
        return ResponseEntity.ok(tradeService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> show(@PathVariable long id) {

        Trade trade = tradeService.findById(id);

        if (trade == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(trade);
    }

    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> create(@RequestBody @Valid TradeCreateEntity newTrade, BindingResult result) {
        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);
        }

        Trade trade = new Trade();
        trade.setDate(new Date());

        trade.setUser(userService.findById(newTrade.userId));


        trade.setTradeables(newTrade.tradeables.stream().map((tradeable) -> {
            Tradeable newTradeable = new Tradeable();
            newTradeable.setWeight(tradeable.weight);
            newTradeable.setDescription(tradeable.description);

            newTradeable.setSilverType(silverTypeService.findById(tradeable.silverTypeId));

            newTradeable.setSellPrice(tradeable.weight * newTradeable.getSilverType().getCurrentPrice());

            return tradeableService.save(newTradeable);

        }).collect(Collectors.toSet()));

        tradeService.save(trade);
        return ResponseEntity.ok(newTrade);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid TradeUpdateEntity newTrade, BindingResult result, @PathVariable long id) {

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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Trade trade = tradeService.findById(id);

        if (trade == null) {
            return ResponseEntity.notFound().build();
        }

        tradeService.delete(id);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}/tradeables")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> showTradeables(@PathVariable long id) {
        Trade trade = tradeService.findById(id);

        if (trade == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(trade.getTradeables());
    }

    @GetMapping("/{id}/tradeables/{tradeableId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> showTradeable(@PathVariable long id, @PathVariable long tradeableId) {
        Trade trade = tradeService.findById(id);

        if (trade == null) {
            return ResponseEntity.notFound().build();
        }

        Tradeable tradeable = tradeableService.findById(tradeableId);

        if (tradeable == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tradeable);
    }

    @PostMapping("/{id}/tradeables")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createTradeable(@RequestBody @Valid TradeableCreateEntity newTradeable, BindingResult result, @PathVariable long id) {
        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);
        }

        Trade trade = tradeService.findById(id);

        if (trade == null) {
            return ResponseEntity.notFound().build();
        }

        Tradeable tradeable = new Tradeable();

        tradeable.setWeight(newTradeable.weight);
        tradeable.setDescription(newTradeable.description);
        tradeable.setSilverType(silverTypeService.findById(newTradeable.silverTypeId));

        tradeable.setSellPrice(tradeable.getWeight() * tradeable.getSilverType().getCurrentPrice());

        tradeable.setTrade(trade);

        tradeableService.save(tradeable);

        return ResponseEntity.ok(tradeable);

    }

    @DeleteMapping("/{id}/tradeables/{tradeableId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteTradeable(@PathVariable long id, @PathVariable long tradeableId) {
        Trade trade = tradeService.findById(id);

        if (trade == null) {
            return ResponseEntity.notFound().build();
        }

        Tradeable tradeable = tradeableService.findById(tradeableId);

        if (tradeable == null) {
            return ResponseEntity.notFound().build();
        }

        tradeableService.delete(tradeableId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/tradeables/{tradeableId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateTradeable(@RequestBody @Valid TradeableUpdateEntity newTradeable, BindingResult result, @PathVariable long id, @PathVariable long tradeableId) {
        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);
        }

        Trade trade = tradeService.findById(id);

        if (trade == null) {
            return ResponseEntity.notFound().build();
        }

        Tradeable tradeable = tradeableService.findById(tradeableId);

        if (tradeable == null) {
            return ResponseEntity.notFound().build();
        }

        tradeable.setWeight(newTradeable.weight);
        tradeable.setDescription(newTradeable.description);
        tradeable.setSilverType(silverTypeService.findById(newTradeable.silverTypeId));
        tradeable.setSellPrice(newTradeable.sellPrice);
        tradeable.setTrade(trade);

        tradeableService.save(tradeable);

        return ResponseEntity.ok(tradeable);

    }


}
