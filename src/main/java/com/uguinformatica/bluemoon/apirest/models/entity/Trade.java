package com.uguinformatica.bluemoon.apirest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "trade", schema = "public")
@Getter
@Setter
@ToString

public class Trade {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    @Min(value = 1, message = "The id must be valid")
    private long id;

    @Basic
    @Column(name = "date")
    private Date date;

    @Basic
    @Column(name = "validated default false")
    private Boolean validated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore

    private User user;

    @OneToMany(mappedBy = "trade")
    @JsonIgnore
    private Set<Tradeable> tradeables;

}
