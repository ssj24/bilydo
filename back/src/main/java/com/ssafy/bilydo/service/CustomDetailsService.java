package com.ssafy.bilydo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.bilydo.jpa.UserLocalRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomDetailsService implements UserDetailsService{
	private final UserLocalRepo userLocalRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userLocalRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("사용자가 없습니다."));
	}
	
}
