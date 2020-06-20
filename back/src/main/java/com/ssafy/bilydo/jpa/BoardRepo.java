package com.ssafy.bilydo.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.bilydo.entity.Board;
import com.ssafy.bilydo.help.ProductNameCnt;

@Repository
public interface BoardRepo extends JpaRepository<Board, Integer>, JpaSpecificationExecutor<Board> {

	Page<Board> findAllById(Integer id, Pageable page);
	Optional<Board> findById(Integer id);
	Page<Board> findAllByProducerId(Integer producerId, Pageable page);
	Page<Board> findAllByCategory(String category, Pageable page);
	Page<Board> findAllByProductNameIgnoreCaseContainingAndCategory(String productName, String category, Pageable page);
	Page<Board> findAllByProductNameIgnoreCaseContaining(String productName, Pageable pageable);
	Long countById(Integer id);
	//@Query(value = "select product_name as productName, count(*) as cnt from board where product_name like concat('%', :productName,'%') group by product_name", nativeQuery = true)
	@Query("SELECT " + 
			" new com.ssafy.bilydo.help.ProductNameCnt(v.productName, COUNT(v)) "+
			" FROM Board v " +
			" WHERE v.productName like concat('%', ?1,'%') group by v.productName ORDER BY 2 DESC "
			)
	List<ProductNameCnt> findAllLikeName(String productName);
}
