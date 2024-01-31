package com.uguinformatica.bluemoon.apirest.models.entity;

import jakarta.persistence.*;
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
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "price")
    private double price;

    @Basic
    @Column(name = "img")
    private String img;

    @Basic
    @Column(name = "disabled", columnDefinition = "boolean default false")
    private boolean disabled;

    @OneToMany(mappedBy = "product")
    Set<ProductOrder> orders;

    @OneToMany(mappedBy = "product")
    private List<Cart> carts;
}
