package com.uguinformatica.bluemoon.apirest.models.entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ProductOrderKey implements Serializable {

    @Column(name = "product_id")
    private long productId;

    @Column(name = "order_id")
    private long orderId;
}
