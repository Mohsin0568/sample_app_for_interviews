/**
 * 
 */
package com.systa.demo.flux.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mohsin
 *
 */

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
	
	
	@Id
	private String id;	
	private long walletLimit;
	private String status;	
	private LocalDate createDate;
	private LocalDate updateDate;
	private long transferLimit;
	private long userId;

}
