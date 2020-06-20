package com.ssafy.bilydo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SignUpRequest {
	private String email;
	private String password;
	private String name;
	private String phone;
	private String location;
}
