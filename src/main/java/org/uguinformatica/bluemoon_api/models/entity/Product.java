package org.uguinformatica.bluemoon_api.models.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product", schema = "public")
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
