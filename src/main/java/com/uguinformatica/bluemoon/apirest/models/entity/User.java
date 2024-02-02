package com.uguinformatica.bluemoon.apirest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @JsonIgnore
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_roles",
            joinColumns = {
                    @JoinColumn(name = "user_id",
                            referencedColumnName = "id",
                            nullable = false, updatable = false, insertable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id",
                            nullable = false, updatable = false, insertable = false)}
    )
    private Set<Role> rolesAssociated = new HashSet<>();
}
