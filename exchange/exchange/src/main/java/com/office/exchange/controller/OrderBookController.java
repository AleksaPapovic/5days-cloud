package com.office.exchange.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.office.exchange.dto.OrderBookDTO;
import com.office.exchange.service.OrderBookService;

@RestController
@RequestMapping(value = "/orderbook", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderBookController {

	private OrderBookService orderBookService;

	public OrderBookController(OrderBookService orderBookService) {
		super();
		this.orderBookService = orderBookService;
	}

	@GetMapping()
	public ResponseEntity<OrderBookDTO> getAll() {
		return new ResponseEntity<OrderBookDTO>(orderBookService.getAll(), HttpStatus.OK);
	}
}
