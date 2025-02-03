package com.swisspost.cryptowallet.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asset_sequence")
    @SequenceGenerator(name = "asset_sequence", sequenceName = "asset_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private double quantity;

    @Transient
    private double value;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;



}