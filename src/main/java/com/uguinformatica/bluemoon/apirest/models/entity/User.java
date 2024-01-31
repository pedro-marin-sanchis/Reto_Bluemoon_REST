package com.uguinformatica.bluemoon.apirest.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "user", schema = "public")
@Getter @Setter
@ToString
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "username")

    private String username;

    @Basic
    @Column(name = "name")
    @NotEmpty(message = "No name specified")
    private String name;

    @Basic
    @Column(name = "surnames")
    private String surnames;

    @Basic
    @Column(name = "email")
    @NotEmpty(message = "No email specified")
    private String email;

    @Basic
    @Column(name = "address")
    private String address;


    @Basic
    @Column(name = "password_hash")
    @NotEmpty(message = "No password specified")
    private String password;

    @Basic
    @Column(name = "balance")
    private double balance;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Cart> productsInCart  ;

    @OneToMany(mappedBy = "user")
    private List<Trade> trades;

}
