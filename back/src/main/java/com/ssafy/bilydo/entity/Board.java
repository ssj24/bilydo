package com.ssafy.bilydo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@Entity
@Table(name = "board")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Board {

	public final static String STATE_READY 	= "READY";
	public static final String STATE_END 	= "END";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String productName;
	private String category;
	private Integer producerId;
	private String usedTime;
	private LocalDateTime borrowableSrt;
	private LocalDateTime borrowableEnd;
	private String subscript;
	private String state;
	
	//private String imageDirPath;
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> imagesPath = new ArrayList<String>();
	
	private Integer priceDay;
	private Integer priceWeek;
	private Integer priceMonth;
	private LocalDateTime registDate;
	private String location;
	
}
