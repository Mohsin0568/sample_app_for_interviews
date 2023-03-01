/**
 * 
 */
package com.systa.demo.restapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.systa.demo.restapi.domain.Wallet;

/**
 * @author mohsin
 *
 */

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long>{

}
