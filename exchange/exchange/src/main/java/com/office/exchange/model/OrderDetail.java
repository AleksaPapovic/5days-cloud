package com.office.exchange.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetail {

	BigDecimal price;
	BigDecimal currentQuantity;
	
}
