package com.uguinformatica.bluemoon.apirest.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "user", schema = "public")
@Getter
@Setter
@ToString
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "username", unique = true, nullable = false)
    @NotEmpty(message = "No username specified")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Basic
    @Column(name = "name")
    @NotEmpty(message = "No name specified")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    private String name;

    @Basic
    @Column(name = "surnames")
    private String surnames;

    @Basic
    @Column(name = "email", unique = true, nullable = false)
    @NotEmpty(message = "No email specified")
    @Size(min=3, message = "Email must be greater than 3 characters")
    private String email;

    @Basic
    @Column(name = "address")
    @NotEmpty(message = "No address specified")
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
    private List<CartItem> productsInCart;

    @OneToMany(mappedBy = "user")
    private List<Trade> trades;

}
