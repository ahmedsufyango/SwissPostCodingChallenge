package com.swisspost.cryptowallet.Utils;

import com.swisspost.cryptowallet.Dtos.CreateWalletRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorsClass {

    public String checkValidationsForCreateWallet(CreateWalletRequest request) {

        if (request.getEmail() == null) {
            return "Email is Null";
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(request.getEmail());

        if (!matcher.matches()) {
            return "Please enter email correctly like abc@xyz.com";
        }

        return "true";
    }
}
