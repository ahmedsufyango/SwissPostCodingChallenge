package com.swisspost.cryptowallet.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAssetsResponse {
    private String message;
    private String email;
    private String responseCode;


}
