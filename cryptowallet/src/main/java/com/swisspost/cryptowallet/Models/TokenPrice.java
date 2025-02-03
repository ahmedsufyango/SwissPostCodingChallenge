package com.swisspost.cryptowallet.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TokenPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokenprice_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "tokenprice_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tokenId;

    private String tokenSymbol;

    @Column(nullable = false)
    private Double priceUsd;

    private LocalDateTime updatedAt;


}
