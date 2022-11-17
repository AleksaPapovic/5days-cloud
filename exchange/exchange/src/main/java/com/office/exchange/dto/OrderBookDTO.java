package com.office.exchange.dto;

import java.util.HashMap;
import java.util.List;

import com.office.exchange.model.OrderDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBookDTO {

	List<OrderTotalDTO> BuyOrders; 
	List<OrderTotalDTO> SellOrders;
}
