package com.vobi.bank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.vobi.bank.domain.Users;
import com.vobi.bank.domain.DocumentType;
import com.vobi.bank.domain.UserType;
import com.vobi.bank.repository.UserTypeRepository;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
class UsersServiceIT {

    @Autowired
    UsersService usersService;

    @Autowired
    UserTypeRepository userTypeRepository;
    
    @Test
    @Order(1)
    void debeValidarLasDependencias() {
        assertNotNull(usersService);
        assertNotNull(userTypeRepository);
    }
    
    @Test
    @Order(2)
    void debeCrearUser() throws Exception{
    	
    	//Arrange
    	Integer idUserType=1;
    
    	Users user=null;
    	UserType userType=userTypeRepository.findById(idUserType).get();
    	
    	user = new Users();
    	user = new Users();
    	user.setName("Sebas");
    	user.setUserEmail("sebas@gmail.com");
    	user.setUserType(userType);
    	user.setEnable("Y");
    	user.setToken("jklkm");
    	
    	//Act
    	user=usersService.save(user);
    	
    	//Assert
    	 assertNotNull(user,"El user es nulo no se pudo grabar");
    }
    
    @Test
    @Order(3)
    void debeModificarUnCustomer() throws Exception {
    	//Arrange
    	String idUser="sebas@gmail.com";
    	Users user=null;
    	user=usersService.findById(idUser).get();
    	user.setEnable("N");
    	user.setToken("tanukii");
    	
    	//Act
    	user=usersService.update(user);
    	
    	//Assert
    	 assertNotNull(user,"El user es nulo no se pudo grabar");
    }
    
    @Test
    @Order(4)
    void debeBorrarUnCustomer() throws Exception {
    	//Arrange
    	String idUser="sebas@gmail.com";
    	Users user=null;
    	Optional<Users> userOptional=null;
    	
    	assertTrue(usersService.findById(idUser).isPresent(), "No encontró el User");
    	user=usersService.findById(idUser).get();
    	
    	//Act
    	usersService.delete(user);
    	userOptional=usersService.findById(idUser);    	
    	
    	//Assert
    	 assertFalse(userOptional.isPresent(),"El user es nulo no se pudo grabar");
    }
    
    @Test
    @Order(5)
    void debeConsultarTodosLosCustomer() throws Exception {
    	//Arrange
    	List<Users> users=null;
    	
    	//Act
    	users=usersService.findAll();
    	users.forEach(customer->log.info(customer.getUserEmail()));
    	
    	//Assert
    	 assertFalse(users.isEmpty(), "No consultó los Customer");
    }

}
