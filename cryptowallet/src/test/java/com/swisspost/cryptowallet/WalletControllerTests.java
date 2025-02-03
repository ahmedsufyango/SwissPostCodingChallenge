package com.swisspost.cryptowallet;

import com.swisspost.cryptowallet.Dtos.CreateWalletRequest;
import com.swisspost.cryptowallet.Models.Wallet;
import com.swisspost.cryptowallet.Repos.WalletRepo;
import com.swisspost.cryptowallet.Service.Impl.WalletControllerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WalletControllerTests {

    @Mock
    private WalletRepo walletRepo;

    @InjectMocks
    private WalletControllerServiceImpl walletControllerServiceImpl;

    private CreateWalletRequest createWalletRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createWalletRequest = new CreateWalletRequest();
        createWalletRequest.setEmail("test@example.com");
    }

    @Test
    void testCreateWalletSuccessfully() {
        when(walletRepo.findByEmail(createWalletRequest.getEmail())).thenReturn(Optional.empty());


        Wallet mockWallet = new Wallet();
        mockWallet.setEmail(createWalletRequest.getEmail());
        when(walletRepo.save(any(Wallet.class))).thenReturn(mockWallet);


        Wallet createdWallet = walletControllerServiceImpl.createWallet(createWalletRequest);


        assertNotNull(createdWallet);
        assertEquals(createWalletRequest.getEmail(), createdWallet.getEmail());

        verify(walletRepo, times(1)).save(any(Wallet.class));
    }

    @Test
    void testCreateWalletThrowsExceptionWhenWalletExists() {
        Wallet existingWallet = new Wallet();
        existingWallet.setEmail("test@example.com");

        when(walletRepo.findByEmail(createWalletRequest.getEmail())).thenReturn(Optional.of(existingWallet));

        assertThrows(IllegalArgumentException.class, () -> walletControllerServiceImpl.createWallet(createWalletRequest));

        verify(walletRepo, never()).save(any(Wallet.class));
    }
}