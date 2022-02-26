package com.raul.demo.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.raul.demo.domain.User;
import com.raul.demo.domain.dto.UserDTO;
import com.raul.demo.respositories.UserRepository;

class UserServiceImplTest {
	
	private static final Integer ID = 1;
    private static final Integer INDEX = 0;
    private static final String NAME = "João";
    private static final String EMAIL = "joao@mail.com";
    private static final String PASSWORD = "123";

    private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    private static final String E_MAIL_JA_CADASTRADO_NO_SISTEMA = "E-mail já cadastrado no sistema";
	
	@InjectMocks		//@InjectMocks cria uma instância real do obj
    private UserServiceImpl service;			//Classe testada

    @Mock		//@Mock pq n precisa de uma instância real, n precisa acessar o BD
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {		//this faz referência a classe testada (UserServiceImpl)
        MockitoAnnotations.openMocks(this);		
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
    	//Mockito.anyInt() - qualquer inteiro. Para qualquer valor chamado pelo findById, retorne optionalUser
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);		//Retorna um obj do tipo User
        Assertions.assertNotNull(response);

        //1° arg é oq está esperando receber e o 2° argumento é oq está recebendo
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

	@Test
	void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	//Inicializa as variáveis para n ter o valor null
	private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));	//Optional de User
    }
}
