package org.uguinformatica.bluemoon_api.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tradeable", schema = "public")
public class Tradeable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "weight")
    private double weight;

    @Basic
    @Column(name = "sell_price")
    private double sellPrice;

    @Basic
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "trade_id")
    private Trade trade;

    @ManyToOne
    @JoinColumn(name = "silver_type_id")
    private SilverType silverType;

}
