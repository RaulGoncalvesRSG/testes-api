package com.raul.demo.services;

import java.util.List;

import com.raul.demo.domain.User;
import com.raul.demo.domain.dto.UserDTO;

public interface UserService {

	User findById(Integer id);
	List<User> findAll();
    User create(UserDTO obj);
}
