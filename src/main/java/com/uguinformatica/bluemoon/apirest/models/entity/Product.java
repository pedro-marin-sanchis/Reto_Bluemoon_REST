package com.uguinformatica.bluemoon.apirest.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "product", schema = "public")
@Getter
@Setter

public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

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

    @OneToMany(mappedBy = "product")
    Set<ProductOrder> orders;

}
