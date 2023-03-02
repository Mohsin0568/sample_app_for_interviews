package com.systa.demo.flux.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.systa.demo.flux.entity.Wallet;
import com.systa.demo.flux.repository.WalletRepository;



@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class WalletControllerTest {
	
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	WebTestClient webTestClient;
	
	private static String walletControllerURL = "/v1/wallet";
	
	@BeforeEach
	public void setup() {
		
		Wallet wallet1 = new Wallet();
		wallet1.setId("testId");
		wallet1.setUserId(1);
		wallet1.setWalletLimit(1000);
		wallet1.setTransferLimit(100);
		wallet1.setCreateDate(LocalDate.now());
		wallet1.setUpdateDate(LocalDate.now());
		wallet1.setStatus("active");
		
		Wallet wallet2 = new Wallet();
		wallet2.setUserId(1);
		wallet2.setWalletLimit(1000);
		wallet2.setTransferLimit(100);
		wallet2.setCreateDate(LocalDate.now());
		wallet2.setUpdateDate(LocalDate.now());
		wallet2.setStatus("active");
		
		List<Wallet> walletsList = List.of(wallet1, wallet2);
		walletRepository.saveAll(walletsList).blockLast();
		
	}
	
	@AfterEach
	void tearDown() {
		walletRepository.deleteAll().block();
	}

	@Test
	@DisplayName("Post - create wallet test")
	void createWalletTest() {
		
		Wallet wallet = new Wallet();
		wallet.setUserId(1);
		wallet.setWalletLimit(1100);
		wallet.setTransferLimit(110);
		
		webTestClient.post()
			.uri(walletControllerURL)
			.bodyValue(wallet)
			.exchange()
			.expectStatus()
			.isCreated()
			.expectBody(Wallet.class)
			.consumeWith(response -> {
				
				Wallet responseWallet = response.getResponseBody();
				
				assertNotNull(responseWallet);
				assertNotNull(responseWallet.getId());
				assertEquals(1100, responseWallet.getWalletLimit());
				assertEquals(110, responseWallet.getTransferLimit());
				
			});		
	}
	
	@Test
	@DisplayName("Get - get all wallets test")
	void getAllWalletsTest() {
		
		webTestClient.get()
			.uri(walletControllerURL)
			.exchange()
			.expectStatus()
			.isOk()
			.expectBodyList(Wallet.class)
			.hasSize(2);			
		
	}
	
	@Test
	@DisplayName("Get - get wallet by id test")
	void getWalletByIdTest() {
		
		webTestClient.get()
			.uri(walletControllerURL+"/testId")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(Wallet.class)
			.consumeWith(response -> {
				
				Wallet responseWallet = response.getResponseBody();
				assertNotNull(responseWallet);
				assertNotNull(responseWallet.getId());
				assertEquals(1000, responseWallet.getWalletLimit());
				assertEquals(100, responseWallet.getTransferLimit());
				
			});
		
	}
	
	@Test
	@DisplayName("Get - get wallet by id test - id not found")
	void getWalletById_idNotFoundTest() {
		
		webTestClient.get()
			.uri(walletControllerURL+"/invalidId")
			.exchange()
			.expectStatus()
			.isNotFound();
		
	}
	
	@Test
	@DisplayName("PUT - update wallet by id test")
	void updateWalletByIdTest() {
		
		Wallet wallet = new Wallet();
		wallet.setWalletLimit(0);
		wallet.setTransferLimit(0);
		wallet.setStatus("inactive");
		
		webTestClient.put()
			.uri(walletControllerURL+"/testId")
			.bodyValue(wallet)
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(Wallet.class)
			.consumeWith(response -> {
				
				Wallet responseWallet = response.getResponseBody();
				assertNotNull(responseWallet);
				assertEquals(0, responseWallet.getWalletLimit());
				assertEquals(0, responseWallet.getTransferLimit());
				assertEquals("inactive", responseWallet.getStatus());
				
			});
		
	}
	
	@Test
	@DisplayName("PUT - update wallet by id test - id not found")
	void updateWalletById_idNotFoundTest() {
		
		Wallet wallet = new Wallet();
		wallet.setWalletLimit(0);
		wallet.setTransferLimit(0);
		wallet.setStatus("inactive");
		
		webTestClient.put()
			.uri(walletControllerURL+"/invalidId")
			.bodyValue(wallet)
			.exchange()
			.expectStatus()
			.isNotFound();
		
	}
	
	@Test
	@DisplayName("DELETE - delete wallet by id test")
	void deleteWalletByIdTest() {
		
		webTestClient.delete()
		.uri(walletControllerURL+"/invalidId")
		.exchange()
		.expectStatus()
		.isNoContent();
		
	}
	
	
	
}
