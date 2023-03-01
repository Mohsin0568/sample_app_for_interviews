/**
 * 
 */
package com.systa.demo.restapi.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author mohsin
 *
 */

@Data
@Entity
@Table(name="wallet")
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private long walletLimit;
	private String status;
	
	@JsonIgnore
	private LocalDate createDate;
	@JsonIgnore
	private LocalDate updateDate;
	private long transferLimit;
	private long userId;
}
