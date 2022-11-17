package com.office.exchange.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.office.exchange.dto.OrderTotalDTO;
import com.office.exchange.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query(value = " SELECT price as price,SUM(quantity - filled_quantity)as total FROM public.\"order\" as o WHERE o.order_status = 0 and o.type = 0 GROUP BY price order by price", countQuery = " SELECT count(*) FROM public.\"order\" as o WHERE o.order_status = 0 and o.type = 0 GROUP BY price order by price ", nativeQuery = true)
	List<OrderTotalDTO> findBuyOrdersTotal();

	@Query(value = " SELECT price as price,SUM(quantity - filled_quantity)as total FROM public.\"order\" as o WHERE o.order_status = 0 and o.type = 1 GROUP BY price order by price ", countQuery = " SELECT count(*) FROM public.\"order\" as o WHERE o.order_status = 0 and o.type = 1 GROUP BY price order by price ", nativeQuery = true)
	List<OrderTotalDTO> findSellOrdersTotal();

	@Query(value = " SELECT * FROM public.\"order\" as o WHERE o.order_status = 0 and o.type = 1 and o.price <= :bprice order by price ASC,created_date_time DESC", countQuery = " SELECT count(*) FROM public.\"order\" as o WHERE o.order_status = 0 and o.type = 1 and o.price <= :bprice order by price ASC,created_date_time DESC ", nativeQuery = true)
	List<Order> findOptimalSellOrders(BigDecimal bprice);

	@Query(value = " SELECT * FROM public.\"order\" as o WHERE o.order_status = 0 and o.type = 0 and o.price >= :sprice order by price DESC,created_date_time DESC", countQuery = " SELECT count(*) FROM public.\"order\" as o WHERE o.order_status = 0 and o.type = 0 and o.price >= :sprice order by price DESC,created_date_time DESC ", nativeQuery = true)
	List<Order> findOptimalBuyOrders(BigDecimal sprice);
	
	
}
