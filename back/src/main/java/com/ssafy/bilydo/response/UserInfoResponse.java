package com.ssafy.bilydo.response;

import java.io.Serializable;
import java.util.List;

import com.ssafy.bilydo.entity.Board;
import com.ssafy.bilydo.entity.Trade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
public class UserInfoResponse implements Serializable{
	
	private String name;
	private String phone;
	private String Location;
	private Double score;
}
