package com.office.exchange.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.exchange.repository.TradeRepository;
import com.office.exchange.service.TradeService;

@Service
public class TradeServiceImpl implements TradeService {

	private TradeRepository tradeRepository;

	@Autowired
	public TradeServiceImpl(TradeRepository tradeRepository) {
		super();
		this.tradeRepository = tradeRepository;
	}
	
	
	public void deleteAll() {
		tradeRepository.deleteAll();
	}
}
