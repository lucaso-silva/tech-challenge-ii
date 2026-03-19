package br.com.fiap.tech_challenge_ii.menu.infra.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.tech_challenge_ii.menu.infra.controller.dto.MenuItemRequestDTO;
import br.com.fiap.tech_challenge_ii.menu.infra.controller.dto.UpdateMenuItemRequestDTO;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "/sql/menu.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS, config = @SqlConfig(transactionMode = TransactionMode.ISOLATED))
public class MenuItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetMenuFromRestaurant() throws Exception {
        mockMvc.perform(get("/menu/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Feijoada"))
                .andExpect(jsonPath("$[0].price").value(45.90))
                .andExpect(jsonPath("$[0].description").value("Traditional Brazilian black bean stew with pork"))
                .andExpect(jsonPath("$[0].isOnlyLocalConsuption").value(false))
                .andExpect(jsonPath("$[0].photoPath").value("/images/feijoada.jpg"))
                .andExpect(jsonPath("$[0].restaurantId").value(1));

    }

    @Test
    void shouldCreateMenuItems() throws Exception {
        List<MenuItemRequestDTO> items = List.of(
                new MenuItemRequestDTO("Picanha", "Delicious beef", new BigDecimal("59.90"), false,
                        "/images/picanha.jpg"),
                new MenuItemRequestDTO("Suco de Laranja", "Fresh orange juice", new BigDecimal("12.00"), false,
                        "/images/suco.jpg"));

        mockMvc.perform(post("/menu/1")
                .header("x-user-id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(items)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ids", Matchers.hasSize(2)));
    }

    @Test
    void shouldReturnForbiddenWhenNotOwner() throws Exception {
        List<MenuItemRequestDTO> items = List.of(
                new MenuItemRequestDTO("Picanha", "Delicious beef", new BigDecimal("59.90"), false,
                        "/images/picanha.jpg"));

        mockMvc.perform(post("/menu/1")
                .header("x-user-id", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(items)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnBadRequestForMissingUserId() throws Exception {
        List<MenuItemRequestDTO> items = List.of(
                new MenuItemRequestDTO("Picanha", "Delicious beef", new BigDecimal("59.90"), false,
                        "/images/picanha.jpg"));

        mockMvc.perform(post("/menu/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(items)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestForMissingName() throws Exception {
        List<MenuItemRequestDTO> items = List.of(
                new MenuItemRequestDTO("", "Description", new BigDecimal("10.00"), false, null));

        mockMvc.perform(post("/menu/1")
                .header("x-user-id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(items)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestForMissingPrice() throws Exception {
        List<MenuItemRequestDTO> items = List.of(
                new MenuItemRequestDTO("Test Item", "Description", null, false, null));

        mockMvc.perform(post("/menu/1")
                .header("x-user-id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(items)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestForNegativePrice() throws Exception {
        List<MenuItemRequestDTO> items = List.of(
                new MenuItemRequestDTO("Test Item", "Description", new BigDecimal("-10.00"), false, null));

        mockMvc.perform(post("/menu/1")
                .header("x-user-id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(items)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestForDuplicateItem() throws Exception {
        mockMvc.perform(post("/menu/1")
                .header("x-user-id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "[{\"name\":\"Feijoada\",\"description\":\"Traditional dish\",\"price\":45.90,\"isOnlyLocalConsuption\":false,\"photoPath\":null}]"))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturnNoContentWhenDeleteMenuItem() throws Exception {

        mockMvc.perform(delete("/menu/1/1")
                .header("x-user-id", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    void shouldUpdateMenuItem() throws Exception {
        UpdateMenuItemRequestDTO request = new UpdateMenuItemRequestDTO(
                "Updated Feijoada",
                "Updated description",
                new BigDecimal("49.90"),
                true,
                "/images/feijoada-updated.jpg");

        mockMvc.perform(put("/menu/1/1")
                .header("x-user-id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Feijoada"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.price").value(49.90))
                .andExpect(jsonPath("$.isOnlyLocalConsuption").value(true))
                .andExpect(jsonPath("$.photoPath").value("/images/feijoada-updated.jpg"));
    }

    @Test
    void shouldReturnForbiddenWhenUpdateMenuItemNotOwner() throws Exception {
        UpdateMenuItemRequestDTO request = new UpdateMenuItemRequestDTO(
                "Updated Feijoada",
                "Updated description",
                new BigDecimal("49.90"),
                true,
                "/images/feijoada-updated.jpg");

        mockMvc.perform(put("/menu/1/1")
                .header("x-user-id", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnNotFoundWhenUpdateNonExistentMenuItem() throws Exception {
        UpdateMenuItemRequestDTO request = new UpdateMenuItemRequestDTO(
                "Updated Item",
                "Description",
                new BigDecimal("49.90"),
                true,
                "/images/item.jpg");

        mockMvc.perform(put("/menu/1/999")
                .header("x-user-id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenUpdateWithInvalidPrice() throws Exception {
        UpdateMenuItemRequestDTO request = new UpdateMenuItemRequestDTO(
                "Updated Feijoada",
                "Description",
                new BigDecimal("-10.00"),
                true,
                "/images/feijoada.jpg");

        mockMvc.perform(put("/menu/1/1")
                .header("x-user-id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenUpdateWithEmptyName() throws Exception {
        UpdateMenuItemRequestDTO request = new UpdateMenuItemRequestDTO(
                "",
                "Description",
                new BigDecimal("10.00"),
                true,
                "/images/feijoada.jpg");

        mockMvc.perform(put("/menu/1/1")
                .header("x-user-id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenGetMenuForNonExistentRestaurant() throws Exception {
        mockMvc.perform(get("/menu/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnForbiddenWhenDeleteMenuItemNotOwner() throws Exception {
        mockMvc.perform(delete("/menu/1/1")
                .header("x-user-id", 2L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnBadRequestWhenDeleteWithMissingUserId() throws Exception {
        mockMvc.perform(delete("/menu/1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
