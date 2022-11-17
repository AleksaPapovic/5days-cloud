package com.office.exchange.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
public class Trade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long Id;
	Long BuyOrderId;
	Long SellOrderId;
	Date CreatedDateTime;
	BigDecimal Price;
	BigDecimal Quantity;

	public Trade() {}
	public Trade(Long buyOrderId, 
	Long sellOrderId,
	BigDecimal price,
	BigDecimal quantity) {
		BuyOrderId = buyOrderId;
		SellOrderId = sellOrderId;
		Price = price;
	    Quantity = quantity;
	    CreatedDateTime= new Date();
	}
}
