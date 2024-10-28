package com.wipro.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.wipro.model.entities.User;
import com.wipro.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;

import java.util.NoSuchElementException;
import java.util.Optional;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveJogarExceptionQuandoUsuarioNaoExiste() {

        assertThrows(NoSuchElementException.class, () -> userService.findById(1L));

    }

    @Test
    void deveRetornarUsuarioCriadoAoCriarUsuario() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");

        when(userRepository.save(user)).thenReturn(user);

        User userResponse = userService.create(user);
        assertEquals(user, userResponse);
    }

    @Test
    void deveRetornarUsuarioNaConsultaDeUsuarioExistente() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));

        User userResponse = userService.findById(userId);
        assertEquals(user, userResponse);


    }
}