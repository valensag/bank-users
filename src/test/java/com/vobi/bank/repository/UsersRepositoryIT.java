package com.vobi.bank.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.vobi.bank.domain.Customer;
import com.vobi.bank.domain.DocumentType;
import com.vobi.bank.domain.UserType;
import com.vobi.bank.domain.Users;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
class UsersRepositoryIT {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserTypeRepository userTypeRepository;
    
    @Test
    @Order(1)
    void debeValidarLasDependencias() {
        assertNotNull(usersRepository);
        assertNotNull(userTypeRepository);
    }
    
    @Test
    @Order(2)
    void debeCrearUsuario() {
    	
    	//Arrange
    	Integer idUserType=1;
    
    	Users user=null;
    	UserType userType=userTypeRepository.findById(idUserType).get();
    	
    	user = new Users();
    	user.setName("Sebas");
    	user.setUserEmail("sebas@gmail.com");
    	user.setUserType(userType);
    	user.setEnable("Y");
    	user.setToken("jklkm");
    	
    	//Act
    	user=usersRepository.save(user);
    	
    	//Assert
    	 assertNotNull(user,"El user es nulo no se pudo grabar");
    }
    
    @Test
    @Order(3)
    void debeModificarUnUser() {
    	//Arrange
    	String idUser="sebas@gmail.com";
    	Users user=null;
    	user=usersRepository.findById(idUser).get();
    	user.setEnable("N");
    	user.setToken("tanuki");
    	
    	//Act
    	user=usersRepository.save(user);
    	
    	//Assert
    	 assertNotNull(user,"El user es nulo no se pudo grabar");
    }
    
    @Test
    @Order(4)
    void debeBorrarUnUser() {
    	//Arrange
    	String idUser="sebas@gmail.com";
    	Users user=null;
    	Optional<Users> userOptional=null;
    	
    	assertTrue(usersRepository.findById(idUser).isPresent(), "No encontró el User");
    	user=usersRepository.findById(idUser).get();
    	
    	//Act
    	usersRepository.delete(user);
    	userOptional=usersRepository.findById(idUser);    	
    	
    	//Assert
    	 assertFalse(userOptional.isPresent(),"El user es nulo no se pudo grabar");
    }
    
    @Test
    @Order(5)
    void debeConsultarTodosLosUsers() {
    	//Arrange
    	List<Users> users=null;
    	
    	//Act
    	users=usersRepository.findAll();
    	users.forEach(user->log.info(user.getUserEmail()));
    	
    	//Assert
    	 assertFalse(users.isEmpty(), "No consultó los Users");
    }

}
