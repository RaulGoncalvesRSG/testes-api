package com.raul.demo.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raul.demo.domain.User;

public interface UserRespotory extends JpaRepository<User, Integer> {

}
