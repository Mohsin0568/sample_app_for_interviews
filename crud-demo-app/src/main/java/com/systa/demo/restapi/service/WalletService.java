/**
 * 
 */
package com.systa.demo.restapi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.systa.demo.restapi.domain.Wallet;
import com.systa.demo.restapi.repository.WalletRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mohsin
 *
 */

@Service
@Slf4j
public class WalletService {

	@Autowired
	WalletRepository walletRepository;
	
	public Wallet createWallet(Wallet wallet) {
		
		wallet.setCreateDate(LocalDate.now());
		wallet.setUpdateDate(LocalDate.now());
		wallet.setStatus("active");
		wallet = walletRepository.save(wallet);
		log.info("Wallet successfully created with id {}", wallet.getId());
		return wallet;
	}
	
	public List<Wallet> getAllWallets(){
		return Streamable.of(walletRepository.findAll()).toList();
	}
	
	public Wallet getWalletById(long id) {
		
		Optional<Wallet> wallet = walletRepository.findById(id);
		
		if(wallet.isPresent()) 
			return wallet.get();
		else {
			// should throw an exception, where the response status should be not found
			return null;
		}
	}
	
	public Wallet updateWalletById(Wallet wallet, long id) {
		
		Optional<Wallet> optionalWallet = walletRepository.findById(id);
		if(optionalWallet.isPresent()) {
			Wallet walletInDb = optionalWallet.get();
			walletInDb.setStatus("inactive");
			walletInDb.setTransferLimit(0);
			walletInDb.setWalletLimit(0);
			return walletRepository.save(walletInDb);
		}
		else {
			// should throw an exception, where the response status should be not found
			return null;
		}
		
	}
	
	public void deleteWalletById(long id) {
		
		Optional<Wallet> wallet = walletRepository.findById(id);
		
		if(wallet.isEmpty()) {
			// should throw exception with non content status code
		}
		
		walletRepository.deleteById(id);
		
	}
}
