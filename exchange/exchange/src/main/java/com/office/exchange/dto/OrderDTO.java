package com.office.exchange.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.office.exchange.model.Trade;
import com.office.exchange.model.enums.OrderStatus;
import com.office.exchange.model.enums.OrderType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

	Long Id;
	String CurrencyPair;
	Date CreatedDateTime;
	OrderType Type;
	BigDecimal Price;
	BigDecimal Quantity;
	BigDecimal FilledQuantity;
	OrderStatus OrderStatus;
	List<Trade> Trades;
	
}
