package com.ssafy.bilydo.controller;

import java.util.Collections;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bilydo.entity.Board;
import com.ssafy.bilydo.entity.TradeChain;
import com.ssafy.bilydo.entity.UserLocal;
import com.ssafy.bilydo.jpa.UserLocalRepo;
import com.ssafy.bilydo.response.UserInfoResponse;
import com.ssafy.bilydo.service.BoardService;
import com.ssafy.bilydo.service.TradeChainService;
import com.ssafy.bilydo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = {"3. Block Chain"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api/chains")
public class TradeChainController {

	private final TradeChainService tradeChainService;
	//이건 유저 컨드롤러 부분.

    //findall
	@ApiOperation(value = "블록체인들 조회", notes = "블록체인을 전부 조회")
	@GetMapping(value = "")
	public ResponseEntity getChains() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(tradeChainService.findAll());
	}
	

	@ApiOperation(value = "해당 블록체인 조회", notes = "해당 거래 id에 대한 블록체인을 조회")
	@GetMapping(value = "trade/{tradeId}")
	public ResponseEntity getChainByTradeId(@PathVariable int tradeId) {
		TradeChain res = tradeChainService.findByTradeId(tradeId);
		if(res == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 id가 없음");
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(res);
	}
    
    //findbyid
	@ApiOperation(value = "해당 블록체인 조회", notes = "해당 id에 대한 블록체인을 조회")
	@GetMapping(value = "{id}")
	public ResponseEntity getChainById(@PathVariable int id) {
		TradeChain res = tradeChainService.findById(id);
		if(res == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 id가 없음");
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(res);
	}
	
}
