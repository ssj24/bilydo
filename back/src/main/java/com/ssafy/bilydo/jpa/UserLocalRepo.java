package com.ssafy.bilydo.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.bilydo.entity.UserLocal;

@Repository
public interface UserLocalRepo extends JpaRepository<UserLocal, Integer> {
	List<UserLocal> findAll();
	Optional<UserLocal> findById(long id);
	Optional<UserLocal> findByEmail(String email);
}
