package com.ssafy.bilydo.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ssafy.bilydo.entity.Board.BoardBuilder;

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
@Table(name = "trade")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
	
	public static final String STATE_READY  = "READY";
	public static final String STATE_ACCEPT = "ACCEPT";
	public static final String STATE_REFUSE = "REFUSE";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer boardId;
	private Integer productId;
	private Integer producerId;
	private Integer consumerId;
	private LocalDateTime borrowSrt;
	private LocalDateTime borrowEnd;
	private Integer reviewScore;	//nullable
	private String review;			//nullable
	
	private String state;
	private Integer realRentalFee;
	private Integer docId;			//nullable
	
	
	public String blockString() {
		return "Trade [id=" + id + ", boardId=" + boardId + ", productId=" + productId + ", producerId=" + producerId
				+ ", consumerId=" + consumerId + ", borrowSrt=" + borrowSrt + ", borrowEnd=" + borrowEnd
				+ ", realRentalFee=" + realRentalFee + "]";
	}
	
	
}
