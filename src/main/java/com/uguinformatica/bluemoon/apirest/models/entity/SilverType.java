package com.uguinformatica.bluemoon.apirest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "silver_type", schema = "public")
@Getter
@Setter
@ToString
public class SilverType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "name")
    @NotEmpty(message = "The name field cannot be empty")
    @Size(min = 3, max = 50, message = "The name field must be between 3 and 50 characters")
    private String name;

    @Basic
    @Column(name = "current_price")
    @DecimalMin(value = "0.0", message = "The current price must be greater than zero", inclusive = false)
    private double currentPrice;

    @Basic
    @Column(name = "disabled", columnDefinition = "boolean default false")
    private boolean disabled;

    @OneToMany(mappedBy = "silverType")
    @JsonIgnore
    private Set<Tradeable> tradeables;


}
