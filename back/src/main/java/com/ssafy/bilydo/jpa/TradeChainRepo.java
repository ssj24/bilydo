package com.ssafy.bilydo.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.bilydo.entity.TradeChain;
import com.ssafy.bilydo.entity.UserLocal;

@Repository
public interface TradeChainRepo extends JpaRepository<TradeChain, Integer> {
	
	Optional<TradeChain> findByTradeId(Integer tradeId);
	
	@Query(value = "select * from trade_chain order by id desc limit 1", nativeQuery = true)
	Optional<TradeChain> findLastOne();
}
