package com.uguinformatica.bluemoon.apirest.controller;

import com.uguinformatica.bluemoon.apirest.controller.utils.ControllerValidationErrors;
import com.uguinformatica.bluemoon.apirest.models.dao.OrderDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dao.ProductDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dao.ProductOrderDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dao.UserDAOImpl;
import com.uguinformatica.bluemoon.apirest.models.dto.OrderCreateDTO;
import com.uguinformatica.bluemoon.apirest.models.entity.Order;
import com.uguinformatica.bluemoon.apirest.models.entity.Product;
import com.uguinformatica.bluemoon.apirest.models.entity.ProductOrder;
import com.uguinformatica.bluemoon.apirest.models.entity.User;
import com.uguinformatica.bluemoon.apirest.models.entity.keys.ProductOrderKey;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrderDAOImpl ordersService;

    @Autowired
    private UserDAOImpl userService;

    @Autowired
    private ProductOrderDAOImpl productOrderService;

    @GetMapping("/")
    public ResponseEntity<List<?>> showAll() {
        return ResponseEntity.ok(ordersService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable long id) {
        Order order = ordersService.findById(id);

        return ResponseEntity.ok(order);
    }


    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody OrderCreateDTO orderCreate, BindingResult result) {

        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);
        }

        User user = userService.findById(orderCreate.getUserId());

        Order order = new Order();

        order.setUser(user);
        order.setAddress(user.getAddress());

        order.setProducts(user.getProductsInCart().stream().map(cartItem -> {
            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrder(order);
            productOrder.setProduct(cartItem.getProduct());
            productOrder.setQuantity(cartItem.getQuantity());

           ProductOrderKey productOrderKey = new ProductOrderKey();

            productOrderKey.setOrderId(order.getId());
            productOrderKey.setProductId(cartItem.getProduct().getId());

            System.out.println(productOrder);

            productOrder.setId(productOrderKey);
            productOrderService.save(productOrder);

            return productOrder;
        }).collect(Collectors.toSet()));

        order.setDate(new Date());

        ordersService.save(order);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Order order = ordersService.findById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        ordersService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Order order, BindingResult result) {
        Order orderFound = ordersService.findById(id);

        if (orderFound == null) {
            return ResponseEntity.notFound().build();
        }

        if (result.hasFieldErrors()) {
            return ControllerValidationErrors.generateFieldErrors(result);

        }

        order.setId(id);

        ordersService.update(orderFound);
        return ResponseEntity.ok(order);
    }}
