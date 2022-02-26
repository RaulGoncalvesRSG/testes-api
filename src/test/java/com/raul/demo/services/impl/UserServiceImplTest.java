package com.raul.demo.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
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
import com.raul.demo.services.exceptions.DataIntegratyViolationException;
import com.raul.demo.services.exceptions.ObjectNotFoundException;

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
        
        //fail("Not yet implemented");		retorna falha no método
    }
    
    @Test		//Qnd buscar por ID, então retorne um obj não encontrado
    void whenFindByIdThenReturnAnObjectNotFoundException() {

    //Qnd chamar o método findById, lance uma exceção do tipo ObjectNotFoundException. thenThrow faz lançar uma exceção
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try{
            service.findById(ID);
        } 
        catch (Exception ex) {
        	//Assegura q a exceção lançada seja da classe ObjectNotFoundException
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        //Assegura q o obj na posição INDEX é da classe User
        assertEquals(User.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }
    
    @Test		//Create - cenário de sucesso
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.create(userDTO); 	//Mocka a resposta do reposotory.save

        assertNotNull(response);
        //Resposta do método create é do tipo User
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }
    
    @Test		//Create - cenário de erro
    void whenCreateThenReturnAnDataIntegrityViolationException() {
    	//Qnd chamar findByEmail passando qualquer Str, retorne Optional<User>
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(2);		//ID diferente para entrar no catch
            service.create(userDTO);
        } 
        catch (Exception ex) {
        	//Assegura q a exceção lançada é do tipo DataIntegratyViolationException
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(E_MAIL_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());
        }
    }

    @Test		//Update - cenário de sucesso
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.update(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }
    
    @Test		//Update - cenário de erro
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(2);
            service.update(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(E_MAIL_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());
        }
    }

    @Test		//Delete - cenário de sucesso
    void deleteWithSuccess() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        /*doNothing (não faça nd) qnd repository chamar o método deleteById passando qualquer int
        utiliza doNothing qnd o método n retornar nada (void)*/
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);
        //Verifica qnds vezes o repository foi chamado no método deleteById
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test		//Delete - cenário de erro
    void whenDeleteThenReturnObjectNotFoundException() {
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
        try {
            service.delete(ID);
        } 
        catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

	//Inicializa as variáveis para n ter o valor null
	private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));	//Optional de User
    }
}
