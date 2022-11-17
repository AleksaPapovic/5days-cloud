package com.office.exchange.dto;

import java.math.BigDecimal;

import com.office.exchange.model.enums.OrderType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderRequestDTO {
	
	Long Id;
	String CurrencyPair;
	OrderType Type;
	BigDecimal Price;
    BigDecimal Quantity;
	
}
