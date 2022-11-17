package com.office.exchange.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.office.exchange.dto.OrderDTO;
import com.office.exchange.dto.OrderRequestDTO;
import com.office.exchange.service.OrderService;
import com.office.exchange.service.TradeService;
 

@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

	private OrderService orderService;
	private TradeService tradeService;

	public OrderController(OrderService orderService,TradeService tradeService) {
		super();
		this.orderService = orderService;
		this.tradeService = tradeService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> getById(@PathVariable("id") String idString) {
		 Long id = Long.valueOf(idString);
		return new ResponseEntity<OrderDTO>(orderService.getById(id), HttpStatus.OK);
	}
	
	@PostMapping()
	@ResponseBody
	public ResponseEntity<OrderDTO> save(@RequestBody OrderRequestDTO orderRequestDTO) {
		return new ResponseEntity<>(orderService.createOrder(orderRequestDTO), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/all")
	public ResponseEntity<String> deleteAll() {
		orderService.deleteAll();
		tradeService.deleteAll();
		return new ResponseEntity<>(null,HttpStatus.OK);
	}
}
