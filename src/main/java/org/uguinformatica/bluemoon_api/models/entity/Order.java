package org.uguinformatica.bluemoon_api.models.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "order", schema = "public")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

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
    private User user;

    @OneToMany(mappedBy = "order")
    Set<ProductOrder> products;

}
