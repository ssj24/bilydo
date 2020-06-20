package com.ssafy.bilydo.help;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductNameCnt {
	private String productName;
	private Long cnt;
}
