/**
 * 
 */
package com.systa.demo.restapi.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.systa.demo.restapi.domain.Wallet;
import com.systa.demo.restapi.service.WalletService;

/**
 * @author mohsin
 *
 */

@RestController
@RequestMapping("/v1/wallet")
public class CrudController {
	
	@Autowired
	private WalletService walletService;

	@PostMapping
	public ResponseEntity<Wallet> createWallet(@RequestBody @Valid Wallet wallet) {
		wallet = walletService.createWallet(wallet);
		
		URI location = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("{/id}")
						.buildAndExpand(wallet.getId())
						.toUri();
		
		return ResponseEntity
					.created(location)
					.body(wallet);
		
	}
	
	@GetMapping
	public ResponseEntity<List<Wallet>> getAllWallets(
			@RequestParam(required = false, name = "userId") Long userId){
		
		return ResponseEntity.ok().body(walletService.getAllWallets());
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Wallet> getWalletById(@PathVariable("id") Long id){
		
		return ResponseEntity.ok().body(walletService.getWalletById(id));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Wallet> updateWalletById(@PathVariable("id") long id, 
				@RequestBody @Valid Wallet wallet){
		
		return null;
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteWalletById(@PathVariable("id") long id) {
		
		walletService.deleteWalletById(id);
		
	}
}
