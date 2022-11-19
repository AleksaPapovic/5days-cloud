package com.office.exchange.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.exchange.dto.OrderDTO;
import com.office.exchange.dto.OrderRequestDTO;
import com.office.exchange.exception.BadRequestException;
import com.office.exchange.model.Order;
import com.office.exchange.model.Trade;
import com.office.exchange.model.enums.OrderStatus;
import com.office.exchange.model.enums.OrderType;
import com.office.exchange.repository.OrderRepository;
import com.office.exchange.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;

	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}

	public OrderDTO getById(Long id) {
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderRepository.findById(id).get(), orderDTO);
		return orderDTO;
	}

	public OrderDTO createOrder(OrderRequestDTO orderRequestDTO) {

		if (!orderRequestDTO.getCurrencyPair().equals("BTCUSD")) {
			throw new BadRequestException("Please enter valid currency");
		}
		if (!(orderRequestDTO.getType().equals(OrderType.BUY) || orderRequestDTO.getType().equals(OrderType.SELL))) {
			throw new BadRequestException("Please enter valid type");
		}
		if (orderRequestDTO.getPrice().compareTo(new BigDecimal(0)) < 0
				|| orderRequestDTO.getQuantity().compareTo(new BigDecimal(0)) < 0) {
			throw new BadRequestException("Please enter positive value for price or quantity");
		}

		Order order = new Order();
		BeanUtils.copyProperties(orderRequestDTO, order);
		order.setOrderStatus(OrderStatus.OPEN);
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(order, orderDTO);
		if (orderRequestDTO.getType().equals(OrderType.BUY)) {
			orderDTO = tradeBuyOrder(orderDTO);
		}

		if (orderRequestDTO.getType().equals(OrderType.SELL)) {
			orderDTO = tradeSellOrder(orderDTO);
		}
		
		Order newOrder = new Order();
		BeanUtils.copyProperties(orderDTO, newOrder);
		orderRepository.save(newOrder);
		return orderDTO;
	}

	public void deleteAll() {
		orderRepository.deleteAll();
	}

	private OrderDTO tradeBuyOrder(OrderDTO orderDTO) {
		List<Order> sellOrders = orderRepository.findOptimalSellOrders(orderDTO.getPrice());

		for (Order order : sellOrders) {
			if (orderDTO.getOrderStatus() != OrderStatus.CLOSE) {
				if (order.getPrice().multiply(order.getQuantity().subtract(order.getFilledQuantity())).compareTo(
						orderDTO.getPrice().multiply(orderDTO.getQuantity().subtract(orderDTO.getFilledQuantity()))) > 0) {
					BigDecimal tradeAmount =(order.getPrice().multiply(order.getQuantity().subtract(order.getFilledQuantity())).subtract(
							orderDTO.getPrice().multiply(orderDTO.getQuantity().subtract(orderDTO.getFilledQuantity()) ) )).divide(order.getPrice());
					tradeAmount= order.getQuantity().subtract(tradeAmount);
					orderDTO.setFilledQuantity(tradeAmount);
					orderDTO.setOrderStatus(OrderStatus.CLOSE);
					orderDTO.getTrades()
							.add(new Trade(orderDTO.getId(), order.getId(), orderDTO.getPrice(), tradeAmount));
					orderDTO.setTrades(orderDTO.getTrades());

					order.setFilledQuantity(tradeAmount);
					order.getTrades().add(new Trade(orderDTO.getId(), order.getId(), order.getPrice(), tradeAmount));
					order.setTrades(order.getTrades());

					orderRepository.save(order);

				} else if (order.getPrice().multiply(order.getQuantity().subtract(order.getFilledQuantity())).compareTo(
						orderDTO.getPrice().multiply(orderDTO.getQuantity().subtract(orderDTO.getFilledQuantity()))) == 0)  {
					BigDecimal tradeAmount =(orderDTO.getPrice().multiply(orderDTO.getQuantity().subtract(orderDTO.getFilledQuantity())).subtract(
							order.getPrice().multiply(order.getQuantity().subtract(order.getFilledQuantity()) ) )).divide(order.getPrice());
					orderDTO.setFilledQuantity(orderDTO.getQuantity());
					orderDTO.setOrderStatus(OrderStatus.CLOSE);
					orderDTO.getTrades()
							.add(new Trade(orderDTO.getId(), order.getId(), orderDTO.getPrice(), tradeAmount));
					orderDTO.setTrades(orderDTO.getTrades());

					order.setFilledQuantity(order.getQuantity());
					order.setOrderStatus(OrderStatus.CLOSE);
					order.getTrades().add(new Trade(orderDTO.getId(), order.getId(), order.getPrice(), tradeAmount));
					order.setTrades(order.getTrades());
					
					orderRepository.save(order);
					
				} else if (order.getPrice().multiply(order.getQuantity().subtract(order.getFilledQuantity())).compareTo(
						orderDTO.getPrice().multiply(orderDTO.getQuantity().subtract(orderDTO.getFilledQuantity()))) < 0)  {

					BigDecimal tradeAmount =(orderDTO.getPrice().multiply(orderDTO.getQuantity().subtract(orderDTO.getFilledQuantity())).subtract(
							order.getPrice().multiply(order.getQuantity().subtract(order.getFilledQuantity()) ) )).divide(order.getPrice());
					orderDTO.setQuantity(tradeAmount);
					orderDTO.getTrades()
							.add(new Trade(orderDTO.getId(), order.getId(), orderDTO.getPrice(), tradeAmount));
					orderDTO.setTrades(orderDTO.getTrades());

					order.setFilledQuantity(order.getFilledQuantity().add(tradeAmount));
					order.setOrderStatus(OrderStatus.CLOSE);
					order.getTrades().add(new Trade(orderDTO.getId(), order.getId(), order.getPrice(), tradeAmount));
					order.setTrades(order.getTrades());
					orderRepository.save(order);
				}
			} else {
				return orderDTO;
			}
		}

		return orderDTO;
	}

	private OrderDTO tradeSellOrder(OrderDTO orderDTO) {
		List<Order> buyOrders = orderRepository.findOptimalBuyOrders(orderDTO.getPrice());

		for (Order order : buyOrders) {
			if (orderDTO.getOrderStatus() != OrderStatus.CLOSE) {
				if (order.getPrice().multiply(order.getQuantity().subtract(order.getFilledQuantity())).compareTo(
						orderDTO.getPrice().multiply(orderDTO.getQuantity().subtract(orderDTO.getFilledQuantity()))) > 0) {
					BigDecimal tradeAmount =(order.getPrice().multiply(order.getQuantity().subtract(order.getFilledQuantity())).subtract(
							orderDTO.getPrice().multiply(orderDTO.getQuantity().subtract(orderDTO.getFilledQuantity()) ) )).divide(order.getPrice());
					
					tradeAmount= order.getQuantity().subtract(tradeAmount);
					orderDTO.setFilledQuantity(tradeAmount);
					orderDTO.setOrderStatus(OrderStatus.CLOSE);
					orderDTO.getTrades()
							.add(new Trade(orderDTO.getId(), order.getId(), orderDTO.getPrice(), tradeAmount));
					orderDTO.setTrades(orderDTO.getTrades());

					order.setFilledQuantity(tradeAmount);
					order.getTrades().add(new Trade(orderDTO.getId(), order.getId(), order.getPrice(), tradeAmount));
					order.setTrades(order.getTrades());

					orderRepository.save(order);

				} else if (order.getPrice().multiply(order.getQuantity().subtract(order.getFilledQuantity())).compareTo(
						orderDTO.getPrice().multiply(orderDTO.getQuantity().subtract(orderDTO.getFilledQuantity()))) == 0)  {
					BigDecimal tradeAmount =(orderDTO.getPrice().multiply(orderDTO.getQuantity().subtract(orderDTO.getFilledQuantity())).subtract(
							order.getPrice().multiply(order.getQuantity().subtract(order.getFilledQuantity()) ) )).divide(order.getPrice());
					orderDTO.setFilledQuantity(orderDTO.getQuantity());
					orderDTO.setOrderStatus(OrderStatus.CLOSE);
					orderDTO.getTrades()
							.add(new Trade(orderDTO.getId(), order.getId(), orderDTO.getPrice(), tradeAmount));
					orderDTO.setTrades(orderDTO.getTrades());

					order.setFilledQuantity(order.getQuantity());
					order.setOrderStatus(OrderStatus.CLOSE);
					order.getTrades().add(new Trade(orderDTO.getId(), order.getId(), order.getPrice(), tradeAmount));
					order.setTrades(order.getTrades());
					
					orderRepository.save(order);
					
				} else if (order.getPrice().multiply(order.getQuantity().subtract(order.getFilledQuantity())).compareTo(
						orderDTO.getPrice().multiply(orderDTO.getQuantity().subtract(orderDTO.getFilledQuantity()))) < 0)  {

					BigDecimal tradeAmount =(orderDTO.getPrice().multiply(orderDTO.getQuantity().subtract(orderDTO.getFilledQuantity())).subtract(
							order.getPrice().multiply(order.getQuantity().subtract(order.getFilledQuantity()) ) )).divide(order.getPrice());
					orderDTO.setQuantity(tradeAmount);
					orderDTO.getTrades()
							.add(new Trade(orderDTO.getId(), order.getId(), orderDTO.getPrice(), tradeAmount));
					orderDTO.setTrades(orderDTO.getTrades());

					order.setFilledQuantity(order.getQuantity());
					order.getTrades().add(new Trade(orderDTO.getId(), order.getId(), order.getPrice(), tradeAmount));
					order.setTrades(order.getTrades());
					order.setOrderStatus(OrderStatus.CLOSE);
					orderRepository.save(order);
				}
			} else {
				return orderDTO;
			}
		}

		return orderDTO;
	}

}
