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
import com.ssafy.bilydo.entity.UserLocal;
import com.ssafy.bilydo.jpa.UserLocalRepo;
import com.ssafy.bilydo.response.UserInfoResponse;
import com.ssafy.bilydo.service.BoardService;
import com.ssafy.bilydo.service.TradeService;
import com.ssafy.bilydo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = {"1. Users"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api/users")
public class UserController {

	private final UserService userService;
	private final BoardService boardService;
	private final UserLocalRepo userRepo;
	private final TradeService tradeService;
    private final PasswordEncoder passwordEncoder;
	//이건 유저 컨드롤러 부분.


	@ApiOperation(value = "자신의 정보 조회", notes = "개인 정보를 보여준다.")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@GetMapping(value = "user")
	public ResponseEntity findUserById() {
		// SecurityContext에서 인증받은 회원의 정보를 얻어온다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		// 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(email));
		//return responseService.getSingleResult(userLocalRepo.findByEmail(id).orElse(UserLocal.userNull()));
	}
	
	
	@ApiOperation(value = "유저 정보 조회", notes = "해당 유저의 정보를 보여준다.")
	@GetMapping(value = "{id}")
	public ResponseEntity getUserInfo(@PathVariable int id ){

		UserLocal user = userService.getUserById(id);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 유저가 없습니다.");
		}
		
		UserInfoResponse res = userService.getUserInfoResponse(user);

		return ResponseEntity.status(HttpStatus.OK)
				.body(res);
	}
    
	
	@ApiOperation(value = "유저 게시판 조회", notes = "해당 유저가 작성한 게시판을 보여준다.")
	@GetMapping(value = "{id}/boards")
	public ResponseEntity getBoadsById(@PathVariable int id,
								  @ApiParam(value = "페이지 넘버", required = true) @RequestParam int page,
								  @ApiParam(value = "페이지당 물건 수", required = true) @RequestParam int size){

		Pageable pageable = PageRequest.of(page, size, Sort.by("registDate"));
		// 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.

		return ResponseEntity.status(HttpStatus.OK)
				.body(boardService.getMyBoards(id, pageable));
	}
	
	@ApiOperation(value = "자신의 게시판 조회", notes = "로그인 한 유저가 작성한 게시판을 보여준다.")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@GetMapping(value = "user/boards")
	public ResponseEntity getMyBoads(
								  @ApiParam(value = "페이지 넘버", required = true) @RequestParam int page,
								  @ApiParam(value = "페이지당 물건 수", required = true) @RequestParam int size){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		UserLocal user = userService.getUser(email); 
		//여기서 user가 null이면 에러를 리턴.
		Pageable pageable = PageRequest.of(page, size, Sort.by("registDate"));
		// 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.

		return ResponseEntity.status(HttpStatus.OK)
				.body(boardService.getMyBoards(user.getId(), pageable));
	}

	@ApiOperation(value = "자신의 거래 조회", notes = "로그인 한 유저가 요청한 거래들을 보여준다.")
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@GetMapping(value = "user/trades")
	public ResponseEntity getMyTrades(
								  @ApiParam(value = "페이지 넘버", required = true) @RequestParam int page,
								  @ApiParam(value = "페이지당 물건 수", required = true) @RequestParam int size){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		UserLocal user = userService.getUser(email); 
		//여기서 user가 null이면 에러를 리턴.
		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		// 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.

		return ResponseEntity.status(HttpStatus.OK)
				.body(tradeService.getMyTrades(user.getId(), pageable));
	}

	
	@ApiImplicitParams({
		 @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@PutMapping(value = "")
	public UserLocal updateUser(@ApiParam(value = "회원 정보", required =  true) @RequestBody UserLocal rUser) {
		// SecurityContext에서 인증받은 회원의 정보를 얻어온다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		UserLocal userLocal = userService.getUser(email);
		
		userRepo.save(UserLocal.builder()
				.id(userLocal.getId())
                .email(userLocal.getEmail())
                .password(passwordEncoder.encode(rUser.getPassword()))
                .name(rUser.getName())
                .phone(rUser.getPhone())
                .location(rUser.getLocation())
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
		
		return userService.getUser(email);
		//return responseService.getSingleResult(userLocalRepo.findByEmail(id).orElse(UserLocal.userNull()));
	}
	
	
}
