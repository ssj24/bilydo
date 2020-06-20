package com.ssafy.bilydo.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.bilydo.entity.Board;
import com.ssafy.bilydo.help.ProductNameCnt;
import com.ssafy.bilydo.jpa.BoardRepo;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BoardService{
	
	private final BoardRepo boardRepo;
	
	public static final String awsSaveFolder = "/home/ubuntu/image/";
	public static final String urlFolder = "image/";
	
	public Page<Board> getBoardsByCondition(String productName, String category, String location, Pageable pageable) {
		System.out.println(productName.length());
		System.out.println(category.length());
		Page<Board> res = boardRepo.findAll(new Specification<Board>() {
			
			@Override
			public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(productName.length() != 0) {
					predicates.add(criteriaBuilder.like(root.get("productName"), "%" + productName + "%"));
				}
				if(location.length() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("location"), location));
				}
				if(category.length() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("category"), category));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
		return res;
	}
	
	@Transactional
	public Board addBoard(Board board , MultipartFile[] files) throws Exception {
		int fileCnt = 0;
		System.out.println(board.toString());
		LocalDateTime now = LocalDateTime.now();
		board.setRegistDate(now.truncatedTo(ChronoUnit.SECONDS));
		boardRepo.save(board);
		new File(awsSaveFolder + board.getId()).mkdir();
		String fileDir = awsSaveFolder + board.getId() + "/";
		for(MultipartFile file : files ) {
			if (!file.isEmpty()) {
				fileCnt += 1;
				String filename = file.getOriginalFilename();
				String filenameExtension = FilenameUtils.getExtension(filename).toLowerCase();
				File destinationFile;
				String destinationFileName;
				//String fileUrl = "C:/BoardSwagger/BoardSwagger/src/main/resources/static/image/";
				
				destinationFileName = fileCnt + "." + filenameExtension;
				destinationFile = new File(fileDir + destinationFileName);

				System.out.println("File : " + destinationFile.getAbsolutePath());
				
				file.transferTo(destinationFile);
				//String saveUrl = "http://192.168.31.122:8197/image/";
				board.getImagesPath().add(urlFolder + board.getId() + "/" + destinationFileName);
			} 
		}
		boardRepo.save(board);
		return board;
	}
	
	public Board updateBoard(Board board) {
		boardRepo.save(board);
		return board;
	}
	
	public Long getCount() {
		return boardRepo.count();
	}
	
	public Long getCountById(Integer id) {
		return boardRepo.countById(id);
	}
	
	public Optional<Board> getBoardById(Integer id){
		return boardRepo.findById(id);
	}
	
	public Page<Board> getMyBoards(Integer producerId, Pageable pageable){
		return boardRepo.findAllByProducerId(producerId, pageable);
	}
	
	public List<ProductNameCnt> getNames(String productName){
		return boardRepo.findAllLikeName(productName);
	}
}
