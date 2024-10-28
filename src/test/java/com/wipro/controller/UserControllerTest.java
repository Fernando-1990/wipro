package com.wipro.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.model.entities.User;
import com.wipro.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindById() throws Exception {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setName("Test User");

        when(userService.findById(userId)).thenReturn(mockUser);

        MvcResult result = mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        User responseUser = objectMapper.readValue(jsonResponse, User.class);

        assertEquals(userId, responseUser.getId());
        assertEquals("Test User", responseUser.getName());
    }

    @Test
    public void testCreateUser() throws Exception {
        User userToCreate = new User();
        userToCreate.setName("New User");

        User createdUser = new User();
        createdUser.setId(2L);
        createdUser.setName("New User");

        when(userService.create(userToCreate)).thenReturn(createdUser);

        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToCreate)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        User responseUser = objectMapper.readValue(jsonResponse, User.class);

        assertEquals(2L, responseUser.getId());
        assertEquals("New User", responseUser.getName());
    }
}