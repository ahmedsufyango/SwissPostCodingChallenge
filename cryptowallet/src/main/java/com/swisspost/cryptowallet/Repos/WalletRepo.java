package com.swisspost.cryptowallet.Repos;


import com.swisspost.cryptowallet.Models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepo extends JpaRepository<Wallet , Long> {

    Optional<Wallet> findByEmail(String email);


}
