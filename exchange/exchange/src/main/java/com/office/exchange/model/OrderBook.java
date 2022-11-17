package com.office.exchange.model;

import java.util.List;

import com.office.exchange.dto.OrderTotalDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBook {

	List<OrderTotalDTO> BuyOrders; 
	List<OrderTotalDTO> SellOrders;
}
	