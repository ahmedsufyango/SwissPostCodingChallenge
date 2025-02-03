package com.swisspost.cryptowallet.Service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swisspost.cryptowallet.Dtos.*;
import com.swisspost.cryptowallet.Models.Asset;
import com.swisspost.cryptowallet.Models.Wallet;
import com.swisspost.cryptowallet.Repos.AssetRepo;
import com.swisspost.cryptowallet.Repos.WalletRepo;
import com.swisspost.cryptowallet.Service.WalletControllerService;
import com.swisspost.cryptowallet.Utils.ValidatorsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WalletControllerServiceImpl implements WalletControllerService {

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private TokenPriceServiceImpl tokenPriceServiceImpl;

    @Autowired
    private AssetRepo assetRepo;



    @Override
    public Wallet createWallet(@Valid CreateWalletRequest request) {
        Optional<Wallet> existingWallet = walletRepo.findByEmail(request.getEmail());
        if (existingWallet.isPresent()) {
            throw new IllegalArgumentException("A wallet with this email already exists.");
        }
        else {
            Wallet wallet = new Wallet();
            wallet.setEmail(request.getEmail());
            return walletRepo.save(wallet);
        }
    }



    @Override
    public Wallet addAssetsToWallet(AddAssetsRequest addAssetsRequest) throws JsonProcessingException {

        Wallet userWallet = walletRepo.findById(addAssetsRequest.getId()).orElse(null);
        if (userWallet == null) {
            return null;
        }


        GetAssetsResponse response = tokenPriceServiceImpl.fetchAndSaveTokenPriceForAddingAssets(addAssetsRequest.getSymbol());

        if (response != null) {
            Asset asset = new Asset();
            asset.setSymbol(response.getData().getSymbol());
            asset.setPrice(response.getData().getPriceUsd());
            asset.setQuantity(addAssetsRequest.getQuantity());
            asset.setWallet(userWallet);


            if (userWallet.getAssets() == null) {
                userWallet.setAssets(new ArrayList<>());
            }
            userWallet.getAssets().add(asset);

            assetRepo.save(asset);
            return walletRepo.save(userWallet);
        }

        return null;
    }

    @Override
    public Boolean checkWalletExistence(String email) {

        Optional<Wallet> wallet = walletRepo.findByEmail(email);

        if (wallet.isEmpty() ){
            return false;
        }else if(wallet.isPresent()){
            return true;
        }

        return null;
    }

    @Override
    public ShowWalletResponse showWallet(ShowWalletRequest request) {

        ShowWalletResponse showWalletResponse = new ShowWalletResponse();

        List<Object> objects = assetRepo.findByWalletId(request.getId());
        if (objects.size() > 0) {
            List<CustomAssetResponse> assets = new ArrayList<>();

            for (int i = 0; i < objects.size(); i++) {
            }

            for (Object resultArray : objects) {
                CustomAssetResponse asset = new CustomAssetResponse();
                Object[] row = (Object[]) resultArray;

                asset.setPrice((String) row[1]);
                asset.setQuantity((double) row[2]);
                asset.setSymbol((String) row[3]);

                double price = Double.parseDouble((String) row[1]);
                double quantity = (double) row[2];
                double value = price * quantity;

                asset.setValue(value);

                assets.add(asset);
                showWalletResponse.setValue(showWalletResponse.getValue() + value);
            }

            showWalletResponse.setId(request.getId());
            showWalletResponse.setCucustomAssetResponse(assets);

            return showWalletResponse;
        }
        return null;
    }

}
