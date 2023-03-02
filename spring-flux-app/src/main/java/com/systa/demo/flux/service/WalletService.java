/**
 * 
 */
package com.systa.demo.flux.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systa.demo.flux.entity.Wallet;
import com.systa.demo.flux.repository.WalletRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author mohsin
 *
 */

@Service
public class WalletService {
	
	@Autowired
	WalletRepository walletRepository;
	
	public Mono<Wallet> createWallet(Wallet wallet){
		
		wallet.setStatus("active");
		wallet.setCreateDate(LocalDate.now());
		wallet.setUpdateDate(LocalDate.now());
		
		return walletRepository.save(wallet);
		
	}
	
	public Flux<Wallet> getAllWallets(){
		return walletRepository.findAll();
	}
	
	public Mono<Wallet> getWalletById(String id){
		return walletRepository.findById(id);
	}
	
	public Mono<Wallet> updateWalletById(Wallet wallet, String id) {
		
		Mono<Wallet> existingWallet = walletRepository.findById(id);		
		
		return existingWallet.flatMap(walletInDb -> {
			
			walletInDb.setStatus(wallet.getStatus());
			walletInDb.setTransferLimit(wallet.getTransferLimit());
			walletInDb.setWalletLimit(wallet.getWalletLimit());
			return walletRepository.save(walletInDb);
			
		});
		
		
	}
	
	public Mono<Void> deleteWalletById(String id) {
		
		return walletRepository.deleteById(id);
		
	}
	

}
