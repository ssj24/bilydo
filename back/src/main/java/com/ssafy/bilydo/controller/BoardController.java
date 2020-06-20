package com.ssafy.bilydo.controller;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.ssafy.bilydo.entity.Board;
import com.ssafy.bilydo.entity.Trade;
import com.ssafy.bilydo.entity.UserLocal;
import com.ssafy.bilydo.help.ProductNameCnt;
import com.ssafy.bilydo.service.BoardService;
import com.ssafy.bilydo.service.TradeService;
import com.ssafy.bilydo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = {"2. Board"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api/boards")
public class BoardController {
	
	private final BoardService boardService;
	private final TradeService tradeService;
	private final UserService userService;
	
	@ApiOperation(value = "해당 게시판 조회", notes = "해당 id에 대한 게시판을 조회")
	@GetMapping(value = "{id}")
	public ResponseEntity getBoards(@PathVariable int id) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(boardService.getBoardById(id));
	}
	
	@ApiOperation(value = "게시판 조회", notes = "조건에 맞는 게시판들을 조회한다.")
	@GetMapping(value = "")
	public ResponseEntity getBoards(@ApiParam(value = "물건 이름") @RequestParam(required = false , defaultValue = "") String productName,
								 @ApiParam(value = "카테고리") @RequestParam(required = false , defaultValue = "") String category,
								 @ApiParam(value = "지역") @RequestParam(required = false, defaultValue = "") String location,
								 @ApiParam(value = "페이지 넘버", required = true) @RequestParam int page,
								 @ApiParam(value = "페이지당 목록 수", required = true) @RequestParam int size) {
		Pageable condition = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"registDate"));
		productName = productName.trim();
		category = category.trim();
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(boardService.getBoardsByCondition(productName, category, location,condition));
	}
	
	@ApiOperation(value = "게시판의 상품 이름 조회", notes = "조건에 맞는 게시물의 상품이름들을 리턴")
	@GetMapping(value = "name")
	public ResponseEntity getNames(@ApiParam(value = "물건 이름") @RequestParam String productName) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(boardService.getNames(productName));
	}

	//게시판 추가.
	@ApiOperation(value = "게시판 생성", notes = "게시판을 생성하고 생성된 게시판을 리턴")
	@PostMapping(value = "board")
	public ResponseEntity addBoard(
			@RequestParam(value = "boardString") String rBoard ,
			@RequestParam("files") MultipartFile[] files) throws Exception {
		System.out.println(files + ", " + files.length);
		//Gson gson = new Gson();
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			@Override
			public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				// TODO Auto-generated method stub
				return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")); }
		}).create();
			
		
		//출처: https://kimyhcj.tistory.com/359 [기억과 기록]
		Board board = gson.fromJson(rBoard, Board.class);
		Board res = boardService.addBoard(board, files);
		System.out.println(res.toString());
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	//게시판 업데이트.
	@ApiOperation(value = "게시판 업데이트", notes = "게시판을 수정")
	@PutMapping(value = "board")
	public ResponseEntity updateBoard(
			@RequestParam(value = "boardString") String rBoard) throws Exception {
		//Gson gson = new Gson();
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			@Override
			public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				// TODO Auto-generated method stub
				return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")); }
		}).create();
		
		Board board = gson.fromJson(rBoard, Board.class);
		Board res = boardService.updateBoard(board);
		System.out.println(res.toString());
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	
	
	@ApiOperation(value = "해당 게시물의 렌탈 요청하기", notes = "해당 게시물에 있는 요청들을 보여주기")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@PostMapping(value = "user/{boardId}/request")
	public ResponseEntity addRequest(@PathVariable Integer boardId,
									@ApiParam(value="렌탈 양식") @RequestBody Trade trade){
		// SecurityContext에서 인증받은 회원의 정보를 얻어온다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		UserLocal userLocal = userService.getUser(email);
		Board board = boardService.getBoardById(boardId).orElse(null);
		if(board == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 게시물이 없음.");
		}
		System.out.println("Board by id : " + board);
		trade.setConsumerId(userLocal.getId());
		trade.setProducerId(board.getProducerId());
		trade.setState(Trade.STATE_READY);
		//여기에 렌탈요첨 확인.
		LocalDateTime bSrt = board.getBorrowableSrt();
		LocalDateTime bEnd = board.getBorrowableEnd();
		if( !( bSrt.isBefore(trade.getBorrowSrt()) && bEnd.isAfter(trade.getBorrowEnd())) ){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("렌탈 가능한 날짜 범위를 주세요.");
		}
		
		Trade res = tradeService.addTrade(trade);
		System.out.println("Trade : " + trade);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	
	@ApiOperation(value = "해당 게시물의 렌탈 요청들 보여주기", notes = "해당 게시물에 있는 요청들을 보여주기")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@GetMapping(value = "user/{boardId}/requests")
	public ResponseEntity getTradesByBoardId(@PathVariable Integer boardId,
								      @ApiParam(value = "trade 상태") @RequestParam(required = false , defaultValue = "") String state){
      
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		UserLocal userLocal = userService.getUser(email);
		Board board = boardService.getBoardById(boardId).orElse(null);
		if(board == null || board.getProducerId() != userLocal.getId()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("board가 없거나, board 작성자가 아님");
		}
		
		List<Trade> res = tradeService.getTradesByCondition(boardId, state);
		System.out.println(res.toString());
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	
	
	@ApiOperation(value = "해당 렌탈 요청을 보여주기", notes = "해당 게시물에 있는 요청들을 보여주기")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@GetMapping(value = "user/requests/{tradeId}")
	public ResponseEntity getTradeById(@PathVariable Integer tradeId){
      
		Trade res = tradeService.getTradeById(tradeId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		UserLocal userLocal = userService.getUser(email);
		if(res == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 trade id가 없음");
		}
		if(res.getConsumerId() != userLocal.getId() && res.getProducerId() != userLocal.getId() ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 trade의 당사자가 아닙니다.");
		}
		System.out.println(res.toString());
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	
	@ApiOperation(value = "해당 렌탈 요청을 수정하기", notes = "해당 렌탈 요청을 수정하는 부분.")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@PutMapping(value = "user/requests/update/{tradeId}")
	public ResponseEntity updateTradeById(@PathVariable Integer tradeId,
										@RequestBody Trade trade){
      
		//Trade res = tradeService.getTradeById(tradeId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		UserLocal userLocal = userService.getUser(email);
		if(trade == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 trade id가 없음");
		}
		if(trade.getConsumerId() != userLocal.getId()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 trade의 당사자가 아닙니다.");
		}
		System.out.println(trade.toString());
		trade = tradeService.addTrade(trade);
		return ResponseEntity.status(HttpStatus.OK).body(trade);
	}
	
	
	@ApiOperation(value = "해당 게시물의 특정 렌탈 요청 승인", notes = "해당 게시물에 있는 요청의 상태를 업데이트함")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@PutMapping(value = "user/requests/{tradeId}")
	public ResponseEntity acceptTrade(@PathVariable Integer tradeId){
      
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		UserLocal userLocal = userService.getUser(email);

		Trade res = tradeService.getTradeById(tradeId);
		if(res == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 trade id가 없음");
		}
		if(res.getProducerId() != userLocal.getId() ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 trade의 당사자가 아닙니다.");
		}
		res = tradeService.acceptTrade(res.getBoardId(), tradeId);
		System.out.println(res);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	
}
