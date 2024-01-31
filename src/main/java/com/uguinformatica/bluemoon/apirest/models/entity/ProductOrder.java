package com.uguinformatica.bluemoon.apirest.models.entity;

import com.uguinformatica.bluemoon.apirest.models.entity.keys.ProductOrderKey;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class ProductOrder implements Serializable {
    @EmbeddedId
    ProductOrderKey id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;
}
