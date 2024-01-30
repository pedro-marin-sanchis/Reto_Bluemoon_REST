package org.uguinformatica.bluemoon_api.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CartKey implements Serializable {
    @Column(name = "product_id")
    private int productId;

    @Column(name = "user_id")
    private int userID;
}
