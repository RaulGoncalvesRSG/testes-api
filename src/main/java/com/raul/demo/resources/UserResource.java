package com.raul.demo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.raul.demo.domain.User;
import com.raul.demo.domain.dto.UserDTO;
import com.raul.demo.services.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	private static final String ID = "/{id}";
	
	@Autowired
	private UserService service;
	
	@Autowired
	private ModelMapper mapper;

	@GetMapping(value = ID)
	public ResponseEntity<UserDTO> findById(@PathVariable Integer id){
		User obj = service.findById(id);			//map possui Fonte da informação e destino
		return ResponseEntity.ok().body(mapper.map(obj, UserDTO.class));
	}
	
	 @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
	 	List<User> list = service.findAll();
        return ResponseEntity.ok().body(list.stream()
        		.map(x -> mapper.map(x, UserDTO.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO obj) {
    	Integer userId = service.create(obj).getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(userId).toUri();
        return ResponseEntity.created(uri).build();
    }
}
