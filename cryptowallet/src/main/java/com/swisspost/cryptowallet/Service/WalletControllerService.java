package com.swisspost.cryptowallet.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swisspost.cryptowallet.Dtos.AddAssetsRequest;
import com.swisspost.cryptowallet.Dtos.CreateWalletRequest;
import com.swisspost.cryptowallet.Dtos.ShowWalletRequest;
import com.swisspost.cryptowallet.Dtos.ShowWalletResponse;
import com.swisspost.cryptowallet.Models.Wallet;

public interface WalletControllerService {
    Wallet createWallet(CreateWalletRequest createWalletRequest);

    Wallet addAssetsToWallet(AddAssetsRequest addAssetsRequest) throws JsonProcessingException;

    Boolean checkWalletExistence(String email);

    ShowWalletResponse showWallet(ShowWalletRequest request);
}
