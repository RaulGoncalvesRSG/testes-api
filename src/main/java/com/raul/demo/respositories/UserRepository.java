package com.raul.demo.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raul.demo.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
}
