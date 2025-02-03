package com.swisspost.cryptowallet.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomAssetResponse {

    private String symbol;

    private String price;

    private double quantity;

    private double value;

    private String eth;
}
