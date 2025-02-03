package com.swisspost.cryptowallet.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAssetsRequest {

    private Long id;

    private String symbol;

    private Double price;

    private Double quantity;


}
