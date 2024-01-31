package com.uguinformatica.bluemoon.apirest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "order", schema = "public")
@Getter
@Setter
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "date")
    private Date date;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "delivered")
    private boolean delivered;

    @Basic
    @Column(name = "accepted")
    private boolean accepted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "order")
    Set<ProductOrder> products;

}
