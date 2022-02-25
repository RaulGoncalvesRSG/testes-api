package com.raul.demo.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raul.demo.domain.User;
import com.raul.demo.domain.dto.UserDTO;
import com.raul.demo.respositories.UserRepository;
import com.raul.demo.services.UserService;
import com.raul.demo.services.exceptions.DataIntegratyViolationException;
import com.raul.demo.services.exceptions.ObjectNotFoundException;

@Service  //Com o @Service, o Spring consegue identificar esta classe como a implementação do UserService na Injeção de Dependência
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
    private ModelMapper mapper;
	
	@Override
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User create(UserDTO obj) {
    	findByEmail(obj);
        return repository.save(mapper.map(obj, User.class));
    }
    
    @Override
    public User update(UserDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, User.class));
    }
    
    @Override
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }
    
    private void findByEmail(UserDTO obj) {
        Optional<User> user = repository.findByEmail(obj.getEmail());
        
        //isPresent - já existe um usuário no BD
        if(user.isPresent() && !user.get().getId().equals(obj.getId())) {
            throw new DataIntegratyViolationException("E-mail já cadastrado no sistema");
        }
    }
}
