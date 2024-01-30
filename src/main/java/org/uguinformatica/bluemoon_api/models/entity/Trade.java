package org.uguinformatica.bluemoon_api.models.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "trade", schema = "public")
public class Trade {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "date")
    private Date date;

    @Basic
    @Column(name = "validated")
    private Boolean validated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "trade")
    private Set<Tradeable> tradeables;
}
