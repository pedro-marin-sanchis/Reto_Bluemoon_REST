package com.uguinformatica.bluemoon_api.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "silver_type", schema = "public")
@Getter
@Setter

public class SilverType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "name")
   // @NotEmpty(message = "The name field cannot be empty")
    private String name;

    @Basic
    @Column(name = "current_price")
    //@NotEmpty(message = "The current price field cannot be empty")
    private double currentPrice;

    @Basic
    @Column(name = "disabled", columnDefinition = "boolean default false")
    @JsonIgnore
    private boolean disabled;

    @OneToMany(mappedBy = "silverType")
    private Set<Tradeable> tradeables;


}
