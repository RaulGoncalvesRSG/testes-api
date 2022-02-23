package com.raul.demo.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raul.demo.domain.User;
import com.raul.demo.respositories.UserRespotory;
import com.raul.demo.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRespotory respotory;
	
	@Override
	public User findById(Integer id) {
		Optional<User> obj = respotory.findById(id);
		return obj.orElse(null);
	}
}
