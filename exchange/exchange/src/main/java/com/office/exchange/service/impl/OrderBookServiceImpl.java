package com.office.exchange.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.exchange.dto.OrderBookDTO;
import com.office.exchange.repository.OrderRepository;
import com.office.exchange.service.OrderBookService;

@Service
public class OrderBookServiceImpl implements OrderBookService {

	private OrderRepository orderRepository;

	@Autowired
	public OrderBookServiceImpl(OrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}
	@Override
	public OrderBookDTO getAll() {
		OrderBookDTO orderBookDTO = new OrderBookDTO();
		orderBookDTO.setBuyOrders(orderRepository.findBuyOrdersTotal());
		orderBookDTO.setSellOrders(orderRepository.findSellOrdersTotal());
		return orderBookDTO;
	}

}
