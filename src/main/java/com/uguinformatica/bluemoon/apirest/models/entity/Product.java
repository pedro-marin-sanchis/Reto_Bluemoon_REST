package com.uguinformatica.bluemoon.apirest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product", schema = "public")
@Getter
@Setter
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "name")
    @NotEmpty(message = "No name specified")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Basic
    @Column(name = "description")
    @NotEmpty(message = "No description specified")
    @Size(min = 3, max = 100, message = "Description must be between 3 and 100 characters")
    private String description;

    @Basic
    @Column(name = "price")
    @NotNull(message = "No price specified")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private double price;

    @Basic
    @Column(name = "img")
    private String img;

    @Basic
    @Column(name = "disabled", columnDefinition = "boolean default false")
    private boolean disabled;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    Set<ProductOrder> orders;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<CartItem> cartItems;
}
