package com.systa.demo.restapi.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systa.demo.restapi.domain.Wallet;
import com.systa.demo.restapi.repository.WalletRepository;


@SpringBootTest
@AutoConfigureMockMvc
class CrudControllerTest {
	
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
    @DisplayName("Post - create wallet")
    void testCreateWallet() throws Exception {
		
		Wallet wallet = new Wallet();
		wallet.setTransferLimit(1000);
		wallet.setWalletLimit(10000);
		wallet.setUserId(1);
		
		MvcResult result = mockMvc.perform(post("/v1/wallet")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(wallet))) // asJsonString is predefined function which is defined below
						.andExpect(status().isCreated())
						.andExpect(header().exists("location"))
						.andExpect(jsonPath("$.transferLimit", is(1000)))
						.andReturn();
		
		String content = result.getResponse().getContentAsString();
		Wallet response = new ObjectMapper().readValue(content, Wallet.class);
		
		Optional<Wallet> optionalWallet = walletRepository.findById(response.getId());
		
		assertTrue(optionalWallet.isPresent());
		assertEquals(1000, optionalWallet.get().getTransferLimit());
		assertEquals(10000, optionalWallet.get().getWalletLimit());		
    }
	
	@Test
	@DisplayName("GET - get all wallets")
	void getAllWalletsTest() throws Exception {
		
		walletRepository.save(getOneWallet());
		walletRepository.save(getOneWallet());
		
		mockMvc.perform(get("/v1/wallet"))
			.andExpect(status().isOk());		
	}
	
	@Test
	@DisplayName("GET - get wallet by id")
	void getWalletByIdTest() throws Exception {
		
		Wallet wallet = walletRepository.save(getOneWallet());
		MvcResult result =  mockMvc.perform(get("/v1/wallet/"+wallet.getId()))
								.andExpect(status().isOk())
								.andReturn();
		
		String content = result.getResponse().getContentAsString();
		Wallet response = new ObjectMapper().readValue(content, Wallet.class);
		
		assertEquals(1000, response.getWalletLimit());
		assertEquals(100, response.getTransferLimit());
		assertEquals("active", response.getStatus());
		
	}
	
	@Test
    @DisplayName("PUT - update wallet")
	void updateWalletByIdTest() throws Exception {
		
		Wallet wallet = walletRepository.save(getOneWallet());
		
		Wallet walletToBeUpdated = getOneWallet();
		walletToBeUpdated.setStatus("inactive");
		walletToBeUpdated.setTransferLimit(0);
		walletToBeUpdated.setWalletLimit(0);
		
		mockMvc.perform(put("/v1/wallet/"+wallet.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(wallet))) // asJsonString is predefined function which is defined below
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.transferLimit", is(0)))
				.andReturn();

		Optional<Wallet> optionalWallet = walletRepository.findById(wallet.getId());
		
		assertTrue(optionalWallet.isPresent());
		assertEquals(0, optionalWallet.get().getTransferLimit());
		assertEquals(0, optionalWallet.get().getWalletLimit());
		assertEquals("inactive", optionalWallet.get().getStatus());
		
	}
	
	@Test
	@DisplayName("DELETE - delete wallet by id")
	void deleteWalletByIdTest() throws Exception {
		
		Wallet wallet = walletRepository.save(getOneWallet());
		
		Optional<Wallet> walletOptional = walletRepository.findById(wallet.getId());
		
		assertTrue(walletOptional.isPresent());
		
		mockMvc.perform(delete("/v1/wallet/"+wallet.getId()))
			.andExpect(status().isNoContent());
		
		walletOptional = walletRepository.findById(wallet.getId());
		
		assertTrue(walletOptional.isEmpty());
		
	}
	
	
	private Wallet getOneWallet() {
		
		Wallet wallet = new Wallet();
		wallet.setCreateDate(LocalDate.now());
		wallet.setUpdateDate(LocalDate.now());
		wallet.setStatus("active");
		wallet.setUserId(1);
		wallet.setWalletLimit(1000);
		wallet.setTransferLimit(100);
		
		return wallet;
	}
	
	static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
