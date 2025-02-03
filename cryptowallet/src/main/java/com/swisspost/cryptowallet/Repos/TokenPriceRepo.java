package com.swisspost.cryptowallet.Repos;

import com.swisspost.cryptowallet.Models.TokenPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenPriceRepo extends JpaRepository<TokenPrice , Long> {
    Optional<TokenPrice> findByTokenId(String tokenId);

}
