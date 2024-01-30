package org.uguinformatica.bluemoon_api.models.entity;

import jakarta.persistence.*;

@Entity
public class Cart {
    @EmbeddedId
    CartKey id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "user_id")
    private User user;
}
