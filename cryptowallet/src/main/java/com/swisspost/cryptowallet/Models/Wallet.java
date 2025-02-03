package com.swisspost.cryptowallet.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_sequence")
    @SequenceGenerator(name = "wallet_sequence", sequenceName = "wallet_sequence", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonManagedReference
    @OneToMany(mappedBy = "wallet")
    private List<Asset> assets = new ArrayList<>();



}