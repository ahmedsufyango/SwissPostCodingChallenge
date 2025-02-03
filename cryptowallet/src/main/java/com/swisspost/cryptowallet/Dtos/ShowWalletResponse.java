package com.swisspost.cryptowallet.Dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swisspost.cryptowallet.Models.Asset;
import com.swisspost.cryptowallet.Models.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShowWalletResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private double value;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Assets")
    private List <CustomAssetResponse> cucustomAssetResponse;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;



}
