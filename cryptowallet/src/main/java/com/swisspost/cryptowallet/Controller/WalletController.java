package com.swisspost.cryptowallet.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swisspost.cryptowallet.Dtos.*;
import com.swisspost.cryptowallet.Models.Wallet;
import com.swisspost.cryptowallet.Service.WalletControllerService;
import com.swisspost.cryptowallet.Utils.Constants;
import com.swisspost.cryptowallet.Utils.ValidatorsClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class WalletController {




    @Autowired
    private WalletControllerService walletControllerService;
    Logger logger = LoggerFactory.getLogger(WalletController.class);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    ValidatorsClass validatorsClass = new ValidatorsClass();

    @PostMapping(Constants.CREATE_WALLET_ENDPOINT)
    public ResponseEntity <CreateWalletResponse> createWallet(@RequestBody @Validated CreateWalletRequest request) {
        logger.info(Constants.STARTED_METHOD_STRING + gson.toJson(request) + Constants.CREATE_WALLET_ENDPOINT);
        try {
            String checkValidations = validatorsClass.checkValidationsForCreateWallet(request);
            Wallet wallet;
            if (checkValidations == Constants.TRUE_STRING){

            boolean walletExistence = walletControllerService.checkWalletExistence(request.getEmail());

            if (walletExistence == false) {
                wallet = walletControllerService.createWallet(request);
                logger.info(Constants.STARTED_TRY_CLAUSE + gson.toJson(request));


                if (wallet != null) {

                    CreateWalletResponse createWalletResponse = new CreateWalletResponse();
                    createWalletResponse.setEmail(wallet.getEmail());
                    createWalletResponse.setMessage(Constants.WALLET_CREATED_SUCCESSFULLY);
                    logger.info(Constants.CREATE_WALLET_ENDPOINT + Constants.CLOSING_METHOD_STRING + gson.toJson(createWalletResponse));
                    return new ResponseEntity<>(createWalletResponse , HttpStatus.OK);
                } else {
                    CreateWalletResponse createWalletResponse = new CreateWalletResponse();
                    createWalletResponse.setMessage(Constants.WALLET_NOT_CREATED);
                    logger.info(Constants.CREATE_WALLET_ENDPOINT + Constants.CLOSING_METHOD_STRING + gson.toJson(createWalletResponse));
                    return new ResponseEntity<>(createWalletResponse , HttpStatus.BAD_REQUEST);                }
            }else if (walletExistence){
                CreateWalletResponse createWalletResponse = new CreateWalletResponse();

                createWalletResponse.setMessage(Constants.WALLET_ALREADY_CREATED_STRING + request.getEmail());
                createWalletResponse.setEmail(request.getEmail());
                logger.info(Constants.CREATE_WALLET_ENDPOINT + Constants.CLOSING_METHOD_STRING + gson.toJson(createWalletResponse));
                return new ResponseEntity<>(createWalletResponse , HttpStatus.CONFLICT);
            }
            }else
                {
                    CreateWalletResponse createWalletResponse = new CreateWalletResponse();

                    createWalletResponse.setMessage(Constants.THERE_IS_A_VALIDATION_ERROR+ checkValidations);
                    createWalletResponse.setEmail(request.getEmail());
                    logger.info(Constants.CREATE_WALLET_ENDPOINT + Constants.CLOSING_METHOD_STRING + gson.toJson(createWalletResponse));
                    return new ResponseEntity<>(createWalletResponse , HttpStatus.BAD_REQUEST);
                }
            }
            catch (IllegalArgumentException e) {

            CreateWalletResponse createWalletResponse = new CreateWalletResponse();

            createWalletResponse.setEmail(Constants.NO_EMAIL_STRING);
            createWalletResponse.setMessage(Constants.WALLET_NOT_CREATED);
            logger.info(Constants.CREATE_WALLET_ENDPOINT + Constants.CLOSING_METHOD_STRING + gson.toJson(createWalletResponse));
            return new ResponseEntity<>(createWalletResponse , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CreateWalletResponse createWalletResponse = new CreateWalletResponse();
        createWalletResponse.setMessage(Constants.SOMETHING_WENT_WRONG);
        return new ResponseEntity<>(createWalletResponse , HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/addassets")
    public AddAssetsResponse addAssetsRequest (@RequestBody AddAssetsRequest addAssetsRequest){
        AddAssetsResponse addAssetsResponse = new AddAssetsResponse();

        try{
            Wallet wallet = new Wallet();
            wallet = walletControllerService.addAssetsToWallet(addAssetsRequest);

            if (wallet!=null) {
                addAssetsResponse.setMessage("Request has been processed succesfully");
                addAssetsResponse.setResponseCode("00");
            }else {
                addAssetsResponse.setResponseCode("01");
                addAssetsResponse.setMessage("Something went wrong, Either there is no wallet with ID "
                                            +addAssetsRequest.getId() + " Or there is no Token for the symbol "
                                            +addAssetsRequest.getSymbol()+"" );
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return addAssetsResponse;
    }

    @PostMapping("/showWalletInformation")
    public ResponseEntity <ShowWalletResponse> showWalletResponse (@RequestBody ShowWalletRequest request){

        ShowWalletResponse walletResponse = walletControllerService.showWallet(request);
        if (walletResponse == null){
            ShowWalletResponse showWalletResponse = new ShowWalletResponse();
            showWalletResponse.setMessage("Either there are no assets or there's no wallet with the given ID: " +request.getId() );
            return new ResponseEntity<>(showWalletResponse , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(walletResponse , HttpStatus.OK);
    }






    }
