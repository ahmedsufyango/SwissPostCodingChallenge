package com.swisspost.cryptowallet.Repos;

import com.swisspost.cryptowallet.Models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepo extends JpaRepository<Asset , Long> {

    @Query(value = "SELECT * FROM ASSET WHERE WALLET_ID = :id", nativeQuery = true)
    List<Object> findByWalletId(String id);
}
