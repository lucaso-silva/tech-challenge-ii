package br.com.fiap.tech_challenge_ii.user.infra.controller;

import br.com.fiap.tech_challenge_ii.user.core.domain.Owner;
import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import br.com.fiap.tech_challenge_ii.user.core.dto.CreateUserDTO;
import br.com.fiap.tech_challenge_ii.user.core.dto.UpdateUserDTO;
import br.com.fiap.tech_challenge_ii.user.core.usecase.CreateUserUseCase;
import br.com.fiap.tech_challenge_ii.user.core.usecase.DeleteUserUseCase;
import br.com.fiap.tech_challenge_ii.user.core.usecase.GetUserUseCase;
import br.com.fiap.tech_challenge_ii.user.core.usecase.UpdateUserUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @MockBean
    private GetUserUseCase getUserUseCase;

    @MockBean
    private UpdateUserUseCase updateUserUseCase;

    @MockBean
    private DeleteUserUseCase deleteUserUseCase;

    @Test
    void deveCriarUsuario() throws Exception {
        // Arrange
        CreateUserDTO dto = new CreateUserDTO("João", "joao@teste.com", "OWNER");
        User user = new Owner(1L, "João", "OWNER", null); // exemplo

        when(createUserUseCase.create(any(CreateUserDTO.class))).thenReturn(user);

        // Act + Assert
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(createUserUseCase).create(any(CreateUserDTO.class));
    }

    @Test
    void deveListarUsuarios() throws Exception {

        User user1 = new Owner(1L, "João", "OWNER", null);
        User user2 = new Owner(2L, "Maria", "OWNER", null);


        when(getUserUseCase.findAll()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());

        verify(getUserUseCase).findAll();
    }

    @Test
    void deveBuscarUsuarioPorId() throws Exception {
        Long id = 1L;
        User user = new Owner(1L, "João", "OWNER", null);

        when(getUserUseCase.findById(id)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk());

        verify(getUserUseCase).findById(id);
    }

    @Test
    void deveAtualizarUsuario() throws Exception {
        Long id = 1L;
        UpdateUserDTO dto = new UpdateUserDTO("Novo Nome", "OWNER");

        doNothing().when(updateUserUseCase).update(eq(id), any(UpdateUserDTO.class));

        mockMvc.perform(put("/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        verify(updateUserUseCase).update(eq(id), any(UpdateUserDTO.class));
    }

    @Test
    void deveDeletarUsuario() throws Exception {
        Long id = 1L;

        doNothing().when(deleteUserUseCase).delete(id);

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isNoContent());

        verify(deleteUserUseCase).delete(id);
    }
}
