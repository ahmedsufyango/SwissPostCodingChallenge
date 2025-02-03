package com.swisspost.cryptowallet.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swisspost.cryptowallet.Dtos.SaveNewTokenRequest;
import com.swisspost.cryptowallet.Dtos.SaveNewTokenResponse;
import com.swisspost.cryptowallet.Service.Impl.TokenPriceServiceImpl;
import com.swisspost.cryptowallet.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableScheduling
@RestController
@RequestMapping("/api")
public class TokenApis {

    @Autowired
    private TokenService tokenService;

    @Value("token.price.update.interval")
    String tokenPriceInterval;

    @PostMapping("/savenewtoken")
    public SaveNewTokenResponse savenewtoken(@RequestBody SaveNewTokenRequest request) {
        SaveNewTokenResponse saveNewTokenResponse = new SaveNewTokenResponse();
        try {
            TokenPriceServiceImpl tokenPriceService = new TokenPriceServiceImpl();
            tokenPriceService.fetchAndSaveTokenPriceForAddingAssets(request.getTokenId());

            saveNewTokenResponse.setMessage("Token Saved" + request.getTokenId());
            return saveNewTokenResponse;

        }

        catch (IllegalArgumentException | JsonProcessingException e) {


        saveNewTokenResponse.setMessage("New Token has not been saved because" + e);
        saveNewTokenResponse.setResponseCode("01");
            return saveNewTokenResponse;
        }
    }

    @Scheduled(fixedRateString = "${token.price.update.interval}")
    @GetMapping("/updatePrices")
    public void updatePrices() {
        List<String> tokens = List.of("bitcoin", "ethereum","tether");
        tokenService.updateTokenPricesConcurrently(tokens);
    }
}
