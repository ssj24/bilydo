package com.ssafy.bilydo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ssafy.bilydo.entity.Board;
import com.ssafy.bilydo.entity.Trade;
import com.ssafy.bilydo.jpa.BoardRepo;
import com.ssafy.bilydo.jpa.TradeRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeService {
	
	private final TradeRepo tradeRepo;
	private final TradeChainService tradeChainService;
	private final BoardRepo boardRepo;
	
	public Trade addTrade(Trade trade) {
		tradeRepo.save(trade);
		return trade;
	}
	
	public List<Trade> getTradesByCondition(String state) {
		List<Trade> res = tradeRepo.findAll(new Specification<Trade>() {

			@Override
			public Predicate toPredicate(Root<Trade> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<Predicate>();
				if(state.length() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("state"), state));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		return res;
	}
	
	public List<Trade> getTradesByCondition(Integer boardId, String state) {
		
		List<Trade> res = tradeRepo.findAll(new Specification<Trade>() {

			@Override
			public Predicate toPredicate(Root<Trade> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(criteriaBuilder.equal(root.get("boardId"), boardId));
				if(state.length() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("state"), state));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
		return res;
	}
	
	public Page<Trade> getMyTrades(Integer id, Pageable page){
		return tradeRepo.findAllByProducerId(id, page);
	}
	
	public Trade getTradeById(Integer id) {
		return tradeRepo.findById(id).orElse(null);
	}
 
	@Transactional
	public Trade acceptTrade(Integer boardId, Integer tradeId) {
		List<Trade> trades = tradeRepo.findAllByBoardId(boardId);
		for(Trade t : trades) {
			t.setState(Trade.STATE_REFUSE);
		}
		tradeRepo.saveAll(trades);
		Trade trade = tradeRepo.findById(tradeId).orElse(null);
		trade.setState(Trade.STATE_ACCEPT);
		tradeRepo.save(trade);
		tradeChainService.makeChain(trade);
		Board board = boardRepo.findById(boardId).orElse(null);
		board.setState(Board.STATE_END);
		boardRepo.save(board);
		return trade;
	}
	
}
