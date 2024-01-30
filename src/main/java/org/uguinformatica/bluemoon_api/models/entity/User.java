package org.uguinformatica.bluemoon_api.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user", schema = "public")
@Getter @Setter
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "surnames")
    private String surnames;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "balance")
    private double balance;

    @Basic
    @Column(name = "password_hash")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Cart> productsInCart  ;

    @OneToMany(mappedBy = "user")
    private List<Trade> trades;

}
