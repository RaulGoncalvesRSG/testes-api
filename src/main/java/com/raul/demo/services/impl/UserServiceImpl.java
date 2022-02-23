package com.raul.demo.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raul.demo.domain.User;
import com.raul.demo.respositories.UserRepository;
import com.raul.demo.services.UserService;
import com.raul.demo.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository respotory;
	
	@Override
	public User findById(Integer id) {
		Optional<User> obj = respotory.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
}
