package com.swisspost.cryptowallet.Service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swisspost.cryptowallet.Controller.WalletController;
import com.swisspost.cryptowallet.Dtos.GetAssetsResponse;
import com.swisspost.cryptowallet.Models.TokenPrice;
import com.swisspost.cryptowallet.Repos.TokenPriceRepo;
import com.swisspost.cryptowallet.Service.TokenService;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TokenPriceServiceImpl implements TokenService {

    @Autowired
    private TokenPriceRepo tokenPriceRepository;

    @Value("${base-url}")
    private String assetsUrl;

    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();





    public void updateTokenPricesConcurrently(List<String> tokenIds) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (String tokenId : tokenIds) {
            executor.submit(() -> fetchAndSaveTokenPrice(tokenId));
        }

        executor.shutdown();
    }

        public void fetchAndSaveTokenPrice(String tokenId) {
        HttpClient client = HttpClients.createDefault();
        String url = assetsUrl + tokenId;



        HttpGet request = new HttpGet(url);
        request.setHeader("Accept-Encoding", "gzip, deflate");
        request.setHeader("Authorization", "d1856cdb-85dc-4049-807a-b1a5206c1e3f");

        try (CloseableHttpResponse response = (CloseableHttpResponse) client.execute(request)) {

            int statusCode = response.getCode();
            String reasonPhrase = response.getReasonPhrase();

            if (statusCode == 200) {

                String responseBody = EntityUtils.toString(response.getEntity());
                logger.info(responseBody);

                ObjectMapper objectMapper = new ObjectMapper();
                GetAssetsResponse getAssetsResponse = objectMapper.readValue(responseBody, GetAssetsResponse.class);

                String symbol = getAssetsResponse.getData().getSymbol();

                double priceUsd = Double.parseDouble(getAssetsResponse.getData().getPriceUsd());




                TokenPrice tokenPrice = tokenPriceRepository.findByTokenId(tokenId)
                        .orElse(new TokenPrice());
                tokenPrice.setTokenId(tokenId);
                tokenPrice.setTokenSymbol(symbol);
                tokenPrice.setPriceUsd(priceUsd);
                tokenPrice.setUpdatedAt(LocalDateTime.now());


                logger.info("Saving Token price in database");
                tokenPriceRepository.save(tokenPrice);
                logger.info("Token price saved successfully");

            } else {
                logger.info("Error: Received status code " + statusCode + " " + reasonPhrase);
            }
        } catch (IOException | ParseException e) {
            System.err.println("Failed to fetch token price for: " + tokenId);
            e.printStackTrace();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public GetAssetsResponse fetchAndSaveTokenPriceForAddingAssets(String tokenId) throws JsonProcessingException {
        HttpClient client = HttpClients.createDefault();
        String url =  "https://api.coincap.io/v2/assets/" + tokenId;


        HttpGet request = new HttpGet(url);
        request.setHeader("Accept-Encoding", "gzip, deflate");
        request.setHeader("Authorization", "d1856cdb-85dc-4049-807a-b1a5206c1e3f");

        try (CloseableHttpResponse response = (CloseableHttpResponse) client.execute(request)) {

            int statusCode = response.getCode();
            String reasonPhrase = response.getReasonPhrase();

            if (statusCode == 200) {

                String responseBody = EntityUtils.toString(response.getEntity());
                logger.info(responseBody);

                ObjectMapper objectMapper = new ObjectMapper();
                GetAssetsResponse getAssetsResponse = objectMapper.readValue(responseBody, GetAssetsResponse.class);

                String symbol = getAssetsResponse.getData().getSymbol();

                Double priceUsd = Double.parseDouble(getAssetsResponse.getData().getPriceUsd());




                TokenPrice tokenPrice = tokenPriceRepository.findByTokenId(tokenId)
                        .orElse(new TokenPrice());
                tokenPrice.setTokenId(tokenId);
                tokenPrice.setTokenSymbol(symbol);
                tokenPrice.setPriceUsd(priceUsd);
                tokenPrice.setUpdatedAt(LocalDateTime.now());


                logger.info("Saving Token price in database");
                tokenPriceRepository.save(tokenPrice);
                logger.info("Token price saved successfully");

                return getAssetsResponse;

            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}



