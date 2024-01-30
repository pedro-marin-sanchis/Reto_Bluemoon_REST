package org.uguinformatica.bluemoon_api.models.entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ProductOrderKey implements Serializable {

    @Column(name = "product_id")
    private int productId;

    @Column(name = "order_id")
    private int orderId;
}
