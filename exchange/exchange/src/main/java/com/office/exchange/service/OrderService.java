package com.office.exchange.service;

import com.office.exchange.dto.OrderDTO;
import com.office.exchange.dto.OrderRequestDTO;

public interface OrderService {
	public OrderDTO getById(Long id);
	public OrderDTO createOrder(OrderRequestDTO orderRequestDTO);
	public void deleteAll();
}
