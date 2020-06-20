package com.ssafy.bilydo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bilydo.entity.UserLocal;
import com.ssafy.bilydo.jpa.UserLocalRepo;
import com.ssafy.bilydo.request.SignUpRequest;
import com.ssafy.bilydo.response.SignInResponse;
import com.ssafy.bilydo.service.JwtTokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;


@Api(tags =  {"0. Token sign"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api")
public class SignController {

	private final UserLocalRepo userLocalRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

	@GetMapping(value = "users")
	public List<UserLocal> getUsers() {
		return userLocalRepo.findAll();
	}
	
	@ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "signin")
    public ResponseEntity signin(@ApiParam(value = "로그인 정보", required = true) @RequestBody SignUpRequest rUser) {
		String id = rUser.getEmail();
		String password = rUser.getPassword();
		UserLocal user = userLocalRepo.findByEmail(id).orElse(UserLocal.userNull());
		System.out.println(user.toString());
        if (!passwordEncoder.matches(password, user.getPassword())) {
        	System.out.println(password + " : " + user.getPassword());
        	System.out.println(passwordEncoder.encode(password));
        	user = UserLocal.userNull();
        }
        if(user.getEmail() == null) {
        	return ResponseEntity.status(HttpStatus.FORBIDDEN)
        			.body("USER IS NOT FOUNDED");
        }
        SignInResponse res = new SignInResponse(jwtTokenProvider.createToken(String.valueOf(user.getEmail()), user.getRoles())); 
        return ResponseEntity.status(HttpStatus.OK)
            			.body(res);
	 }
	
	@ApiOperation(value = "회원 비밀번호 변경", notes = "이메일의 비밀번호를 변경한다.")
    @PutMapping(value = "set/password")
    public ResponseEntity updatePassword(@ApiParam(value = "로그인 정보", required = true) @RequestBody SignUpRequest rUser) {
		String id = rUser.getEmail();
		String password = rUser.getPassword();
		UserLocal user = userLocalRepo.findByEmail(id).orElse(UserLocal.userNull());
		System.out.println(user.toString());
        if (!passwordEncoder.matches(password, user.getPassword())) {
        	System.out.println(password + " : " + user.getPassword());
        	System.out.println(passwordEncoder.encode(password));
        	user = UserLocal.userNull();
        }
        if(user.getEmail() == null) {
        	return ResponseEntity.status(HttpStatus.FORBIDDEN)
        			.body("USER IS NOT FOUNDED");
        }
        SignInResponse res = new SignInResponse(jwtTokenProvider.createToken(String.valueOf(user.getEmail()), user.getRoles())); 
        return ResponseEntity.status(HttpStatus.OK)
            			.body(res);
	 }
	
	 @ApiOperation(value = "가입", notes = "회원가입을 한다.")
	 @PostMapping(value = "signup")
	 public ResponseEntity signup(@ApiParam(value = "회원가입 정보", required = true) @RequestBody SignUpRequest rUser) {
		String email = rUser.getEmail();
		String password = rUser.getPassword();
		String name = rUser.getName();
		String phone = rUser.getPhone();
		String location = rUser.getLocation();
		System.out.println(rUser.toString());
		UserLocal user = userLocalRepo.findByEmail(email).orElse(UserLocal.userNull());
		if(user.getEmail() == null) {
			userLocalRepo.save(UserLocal.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .location(location)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
			return ResponseEntity.status(HttpStatus.OK)
        			.body("USER IS CREATED");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body("EMAIL IS NOT UNIQUE");
    }
	 
	 @ApiOperation(value = "체크", notes = "이메일 체크 기능")
	 @PostMapping(value = "check")
	 public ResponseEntity checkEmail(@ApiParam(value = "회원 정보 이메일", required = true) @RequestParam String email) {
		UserLocal user = userLocalRepo.findByEmail(email).orElse(UserLocal.userNull());
		if(user.getEmail() == null) {
			return ResponseEntity.status(HttpStatus.OK)
        			.body("EMAIL IS UNIQUE");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body("EMAIL IS NOT UNIQUE");
    }
}
