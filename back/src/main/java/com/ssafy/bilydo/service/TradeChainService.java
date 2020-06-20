package com.ssafy.bilydo.service;

import java.security.MessageDigest;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ssafy.bilydo.entity.Trade;
import com.ssafy.bilydo.entity.TradeChain;
import com.ssafy.bilydo.jpa.TradeChainRepo;
import com.ssafy.bilydo.jpa.TradeRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeChainService {
	
	private final TradeChainRepo tradeChainRepo; 
	private final TradeRepo tradeRepo;
	private static final String ORIGINHASH = "abcd";
	private final int DEPTH = 4;
	private final String TARGET = "0000";
	
	public List<TradeChain> findAll(){
		return tradeChainRepo.findAll();
	}
	
	public TradeChain findById(int id) {
		return tradeChainRepo.findById(id).orElse(null);
	}
	
	public TradeChain findByTradeId(int id) {
		return tradeChainRepo.findByTradeId(id).orElse(null);
	}
	
	public TradeChain makeChain(Trade trade) {
		TradeChain pre = tradeChainRepo.findLastOne().orElse(null);
		if(pre == null) {
			pre = new TradeChain();
			pre.setCurHash(ORIGINHASH);
		}
		System.out.println(pre);
		long nonce = 0;
		String hash = "";
		while(true) {
			hash = makeHash(trade, nonce, pre.getCurHash());
			if(hash.substring(0, DEPTH).equals(TARGET)) {
				break;
			}
			nonce++;
		}
		TradeChain cur = TradeChain.builder()
							.tradeId(trade.getId())
							.nonce(nonce)
							.curHash(hash)
							.build();
		System.out.println(cur);
		tradeChainRepo.save(cur);
		return cur;
	}
	
	public String makeHash(Trade trade, long nonce, String preHash) {
		StringBuffer result = new StringBuffer();
        String input = trade.blockString() + nonce + preHash;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());
            byte bytes[] = md.digest();
            for(int i=0;i<bytes.length;i++) {
                result.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch(Exception e) {
            e.printStackTrace();
        } 
        return result.toString();
	}
}
