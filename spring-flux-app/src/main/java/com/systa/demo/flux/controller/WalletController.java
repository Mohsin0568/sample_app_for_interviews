/**
 * 
 */
package com.systa.demo.flux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.systa.demo.flux.entity.Wallet;
import com.systa.demo.flux.service.WalletService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author mohsin
 *
 */

@RestController
@RequestMapping("/v1/wallet")
public class WalletController {
	
	@Autowired
	WalletService walletService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Wallet> createWallet(@RequestBody Wallet wallet) {
		
		return walletService.createWallet(wallet).log();
		
	}
	
	@GetMapping
	public Flux<Wallet> getAllWallets(@RequestParam(name="userId", required = false) String userId) {
		
		return walletService.getAllWallets().log();
		
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Wallet>> getWalletById(@PathVariable("id") String id){
		
		return walletService.getWalletById(id).log()
				.map(ResponseEntity.ok() :: body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
		
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Wallet>> updateWalletById(@RequestBody Wallet wallet, @PathVariable("id") String id){
		
		return walletService.updateWalletById(wallet, id)
					.map(ResponseEntity.ok() :: body)
					.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> deleteWalletById(@PathVariable("id") String id){		
		return walletService.deleteWalletById(id);
	}
	
}
