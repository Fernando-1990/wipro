package com.wipro.controller;

import com.wipro.model.entities.User;
import com.wipro.model.exceptions.InputException;
import com.wipro.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Busca um usuário pelo ID", description = "Retorna um usuário específico pelo ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })


    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@Parameter(description = "ID do usuário que será buscado", required = true)
                                         @PathVariable Long id) {
        try {
            var user = userService.findById(id);
            return ResponseEntity.ok(user);
        }
        catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Cria um novo usuário", description = "Cria um novo usuário com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))})

    })
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User userToCreate) throws InputException {

        var userCreated = userService.create(userToCreate);
        return ResponseEntity.ok(userCreated);

    }
}
