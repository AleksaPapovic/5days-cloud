package com.office.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.office.exchange.model.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long> {

}
