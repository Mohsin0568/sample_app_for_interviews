/**
 * 
 */
package com.systa.demo.flux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.systa.demo.flux.entity.Wallet;

/**
 * @author mohsin
 *
 */

@Repository
public interface WalletRepository extends ReactiveMongoRepository<Wallet, String>{

}
