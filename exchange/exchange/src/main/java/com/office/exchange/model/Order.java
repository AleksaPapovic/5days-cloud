package com.office.exchange.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.office.exchange.model.enums.OrderStatus;
import com.office.exchange.model.enums.OrderType;

import lombok.Getter;
import lombok.Setter;

@Table(name="`Order`")
@Entity
@Getter
@Setter
public class Order {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long Id;
	String CurrencyPair;
	Date CreatedDateTime;
	OrderType Type;
	@Column(name = "Quantity", precision = 19, scale = 2, columnDefinition="DECIMAL(19,2)") 
	BigDecimal Quantity;
	@Column(name = "Price", precision = 19, scale = 2, columnDefinition="DECIMAL(19,2)") 
	BigDecimal Price;
	@Column(name = "FilledQuantity", precision = 19, scale = 2, columnDefinition="DECIMAL(19,2)") 
	BigDecimal FilledQuantity;
	OrderStatus OrderStatus;
	@OneToMany(targetEntity=Trade.class, mappedBy="Id", fetch=FetchType.EAGER)
	List<Trade> Trades;
	
	
	public Order(){
		FilledQuantity = new BigDecimal(0);
		Trades = new ArrayList<Trade>();
		CreatedDateTime = new Date();
	}
}
