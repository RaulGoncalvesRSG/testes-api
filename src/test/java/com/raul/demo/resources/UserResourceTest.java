package com.raul.demo.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.raul.demo.domain.User;
import com.raul.demo.domain.dto.UserDTO;
import com.raul.demo.services.impl.UserServiceImpl;

@SpringBootTest
class UserResourceTest {

	private static final Integer ID      = 1;
    private static final Integer INDEX   = 0;
    private static final String NAME     = "João";
    private static final String EMAIL    = "joao@mail.com";
    private static final String PASSWORD = "123";

    private User user = new User();
    private UserDTO userDTO = new UserDTO();

    @InjectMocks
    private UserResource resource;

    @Mock
    private UserServiceImpl service;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }
    
    private void startUser() {
        user = new User(ID, NAME, EMAIL,  PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
    	//Mocka as chamadas para os métodos findById e map
        when(service.findById(anyInt())).thenReturn(user);
        //Quando fizer o mapeamento de qualquer classe, retorna UserDTO
        when(mapper.map(any(), any())).thenReturn(userDTO);

        //Mocka o retorno do método resource.findById
        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response);			//Assegura q a resposta n é null
        assertNotNull(response.getBody());
        
        assertEquals(ResponseEntity.class, response.getClass());	//Assegura o tipo da resposta
        //Assegura q o corpo da resposta é do tipo UserDTO
        assertEquals(UserDTO.class, response.getBody().getClass());

        //Assegura os atributos
        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void whenFindAllThenReturnAListOfUserDTO() {
    	//Retorna uma lista de usuários. Neste caso, está colocando apenas um usuário na lists
        when(service.findAll()).thenReturn(List.of(user));
        //Depois da chamada do método findAll, é feito o mapeamento transformando cada User em UserDTO
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        //Assegura q o corpo da resposta é do tipo ArrayList
        assertEquals(ArrayList.class, response.getBody().getClass());
        //Assegura q o obj dentro do ArrayList é do tipo UserDTO
        assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(service.create(any())).thenReturn(user);

        ResponseEntity<UserDTO> response = resource.create(userDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        //Assegua q o header da resposta trouxe a chave "Location"
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(service.update(userDTO)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.update(ID, userDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
    	//doNothing pq o método service.delete n tem retorno 
        doNothing().when(service).delete(anyInt());

        ResponseEntity<UserDTO> response = resource.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        //Verifica se o service.delete é chamado apenas 1x
        verify(service, times(1)).delete(anyInt());
    }
}
