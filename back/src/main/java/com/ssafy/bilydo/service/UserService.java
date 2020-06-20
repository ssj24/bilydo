package com.ssafy.bilydo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.bilydo.entity.Board;
import com.ssafy.bilydo.entity.Trade;
import com.ssafy.bilydo.entity.UserLocal;
import com.ssafy.bilydo.jpa.BoardRepo;
import com.ssafy.bilydo.jpa.TradeRepo;
import com.ssafy.bilydo.jpa.UserLocalRepo;
import com.ssafy.bilydo.response.UserInfoResponse;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final BoardRepo boardRepo;
	
	private final UserLocalRepo userLocalRepo;
	
	private final TradeRepo tradeRepo;
	
	public Page<Board> getBoards(Integer producerId, Pageable pageable) {
		return boardRepo.findAllByProducerId(producerId, pageable);
	}
	
	public UserLocal getUser(String email) {
		return userLocalRepo.findByEmail(email).orElse(UserLocal.userNull());
	}
	
	public UserInfoResponse getUserInfoResponse(UserLocal userLocal) {
		List<Trade> trades = tradeRepo.findAllByProducerId(userLocal.getId());
		double total = 0;
		int cnt = 0;
		for(Trade t : trades) {
			if(t.getReview() != null && t.getReview().length() != 0) {
				cnt +=1 ;
				total += t.getReviewScore();
			}
		}
		double score = 0;
		if(cnt != 0) {
			score = total / cnt;
		}
		UserInfoResponse res = UserInfoResponse.builder()
				.name(userLocal.getName())
				.phone(userLocal.getPhone())
				.Location(userLocal.getLocation())
				.score(score)
				.build();
		return res;
	}
	
	public UserLocal getUserById(Integer id) {
		return userLocalRepo.findById(id).orElse(null);
	}
}
