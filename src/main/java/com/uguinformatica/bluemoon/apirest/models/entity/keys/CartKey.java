package com.uguinformatica.bluemoon.apirest.models.entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartKey implements Serializable {
    @Column(name = "product_id")
    private long productId;

    @Column(name = "user_id")
    private long userId;
}
