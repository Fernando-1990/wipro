package com.wipro.service;

import com.wipro.model.entities.User;
import com.wipro.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, @Value(value = "${nome}") String nome, Environment environment) {

        this.userRepository = userRepository;
        LOGGER.info("AQUIIIII" + nome);
        System.out.println(environment.getProperty("nome"));
    }


    public User findById(Long id) {

        User user = userRepository.findUserById(id).orElseThrow(() -> {
            LOGGER.info("Usuário não encontrado");
            return new NoSuchElementException();
        });
        LOGGER.info("Nome do usuário: " + user.getName());
        return user;
    }

    @Operation(summary = "Creates a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "User already exists", content = @Content)
    })
    public User create(@RequestBody(description = "User to be created", required = true) User userToCreate) {
        if (userRepository.existsById(userToCreate.getId())) {
            throw new IllegalArgumentException("This User number already exists.");
        }
        return userRepository.save(userToCreate);
    }
}
