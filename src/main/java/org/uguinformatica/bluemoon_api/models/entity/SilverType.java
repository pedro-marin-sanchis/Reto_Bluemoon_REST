package org.uguinformatica.bluemoon_api.models.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "silver_type", schema = "public")
public class SilverType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "current_price")
    private double currentPrice;

    @OneToMany(mappedBy = "silverType")
    private Set<Tradeable> tradeables;


}