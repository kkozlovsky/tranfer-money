package ru.kerporation.tranfermoney.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Data
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
}
