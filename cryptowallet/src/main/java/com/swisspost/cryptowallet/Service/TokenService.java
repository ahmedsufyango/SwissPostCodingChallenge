package com.swisspost.cryptowallet.Service;

import java.util.List;

public interface TokenService {
    void updateTokenPricesConcurrently(List<String> tokens);
}
