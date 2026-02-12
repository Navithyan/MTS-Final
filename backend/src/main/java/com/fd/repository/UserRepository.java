package com.fd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fd.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsername(String username);

}
