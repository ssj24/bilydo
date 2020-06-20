package com.ssafy.bilydo.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
@AllArgsConstructor
public class SignInResponse implements Serializable{
	private String accessToken;
}
