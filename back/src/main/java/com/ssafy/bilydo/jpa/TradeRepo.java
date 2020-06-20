package com.ssafy.bilydo.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ssafy.bilydo.entity.Trade;

@Repository
public interface TradeRepo extends JpaRepository<Trade, Integer>, JpaSpecificationExecutor<Trade>  {
	
	List<Trade> findAllByBoardId(Integer id);

	List<Trade> findAllByConsumerId(Integer id);

	List<Trade> findAllByProducerId(Integer id);
	
	Page<Trade> findAllByProducerId(Integer id, Pageable page);
}
