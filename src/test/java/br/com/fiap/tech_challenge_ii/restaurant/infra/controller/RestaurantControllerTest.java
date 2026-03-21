package br.com.fiap.tech_challenge_ii.restaurant.infra.controller;

import br.com.fiap.tech_challenge_ii.restaurant.helper.RestaurantHelper;
import br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json.AddressJson;
import br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json.CreateRestaurantRequest;
import br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json.GetRestaurantResponse;
import br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json.UpdateRestaurantRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = {"/sql/restaurant/clean-up.sql",
            "/sql/restaurant/create-restaurant-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void create_shouldCreateRestaurant_whenUserTypeIsOwner() throws Exception {
        CreateRestaurantRequest request = RestaurantHelper.buildCreateRestaurantRequest();

        var result = mockMvc.perform(post("/restaurants")
                    .header("x-user-id", 200L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("any-restaurant-name"))
                .andExpect(jsonPath("$.kitchenType").value("ITALIAN"))
                .andExpect(header().string("Location", Matchers.matchesPattern("/restaurants/\\d+")))
                .andReturn();

        var responseBody = result.getResponse().getContentAsString();
        GetRestaurantResponse restaurantResponse = objectMapper.readValue(responseBody, GetRestaurantResponse.class);
        Long id = restaurantResponse.id();

        mockMvc.perform(get("/restaurants/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("any-restaurant-name"))
                .andExpect(jsonPath("$.kitchenType").value("ITALIAN"));
    }

    @Test
    @Sql(scripts = "/sql/restaurant/clean-up.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void create_shouldThrowUserNotFoundException_whenUserNotFound() throws Exception {
        CreateRestaurantRequest request = RestaurantHelper.buildCreateRestaurantRequest();

        mockMvc.perform(post("/restaurants")
                    .header("x-user-id", 900L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("There is no user with id 900"));
    }

    @Test
    @Sql(scripts = {"/sql/restaurant/clean-up.sql",
                    "/sql/restaurant/create-restaurant-setup.sql"},
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void create_shouldThrowUnauthorizedOperationException_whenUserIsNotTypeOwner() throws Exception {
        CreateRestaurantRequest request = RestaurantHelper.buildCreateRestaurantRequest();

        mockMvc.perform(post("/restaurants")
                        .header("x-user-id", 201L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.detail").value("Only users with OWNER role can create restaurants"));
    }

    @Test
    @Sql(scripts = {"/sql/restaurant/clean-up.sql",
            "/sql/restaurant/create-restaurant-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void create_shouldThrowDomainException_whenNameIsMissing() throws Exception{
        CreateRestaurantRequest invalidRequest = new CreateRestaurantRequest(
                "",
                RestaurantHelper.buildCreateRestaurantRequest().address(),
                "Italian",
                "any-opening-hours"
        );

        mockMvc.perform(post("/restaurants")
                    .header("x-user-id", 200L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Validation failed"));
    }

    @Test
    @Sql(scripts = {"/sql/restaurant/clean-up.sql",
            "/sql/restaurant/restaurant-setup.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void get_shouldReturnARestaurant_whenIdIsValid() throws Exception {
        mockMvc.perform(get("/restaurants/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("any-restaurant-name"))
                .andExpect(jsonPath("$.address.street").value("any-street-name"))
                .andExpect(jsonPath("$.address.city").value("any-city"))
                .andExpect(jsonPath("$.kitchenType").value("BRAZILIAN"))
                .andExpect(jsonPath("$.openingHours").value("any-opening-hours"));
    }

    @Test
    @Sql(scripts = "/sql/restaurant/clean-up.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void get_shouldThrowRestaurantNotFoundException_whenRestaurantIdIsNotValid() throws Exception {
        mockMvc.perform(get("/restaurants/999"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Restaurant not found"));
    }

    @Test
    @Sql(scripts = {"/sql/restaurant/clean-up.sql",
            "/sql/restaurant/list-restaurants-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void list_shouldReturnAListOfRestaurants_whenThereAreRestaurants() throws Exception {
        mockMvc.perform(get("/restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("first-restaurant-name"))
                .andExpect(jsonPath("$[0].kitchenType").value("BRAZILIAN"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("second-restaurant-name"))
                .andExpect(jsonPath("$[1].kitchenType").value("ITALIAN"));
    }

    @Test
    @Sql(scripts = "/sql/restaurant/clean-up.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void list_shouldReturnEmptyList_whenThereAreNoRestaurants() throws Exception {
        mockMvc.perform(get("/restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Sql(scripts = {"/sql/restaurant/clean-up.sql",
            "/sql/restaurant/restaurant-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void delete_shouldDeleteRestaurant_whenIdIsValidAndUserIsOwner() throws Exception {
        mockMvc.perform(delete("/restaurants/1")
                        .header("x-user-id", 200)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/restaurants/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Restaurant not found"));
    }

    @Test
    @Sql(scripts = {"/sql/restaurant/clean-up.sql",
            "/sql/restaurant/restaurant-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void delete_shouldThrowRestaurantNotFoundException_whenRestaurantIdIsNotValid() throws Exception {
        mockMvc.perform(delete("/restaurants/999")
                    .header("x-user-id", 200)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Restaurant not found"));
    }

    @Test
    @Sql(scripts = {"/sql/restaurant/clean-up.sql",
            "/sql/restaurant/restaurant-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void delete_shouldThrowUnauthorizedOperationException_whenUserIsNotOwner() throws Exception {
        mockMvc.perform(delete("/restaurants/1")
                    .header("x-user-id", 2)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.detail").value("User not authorized to delete this restaurant"));
    }

    @Test
    @Sql(scripts = {"/sql/restaurant/clean-up.sql",
            "/sql/restaurant/restaurant-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void patch_shouldUpdateRestaurant_whenDataIsValidAndUserIsOwner() throws Exception {
        UpdateRestaurantRequest updateRestaurantRequest = RestaurantHelper.buildUpdateRestaurantRequest();

        mockMvc.perform(patch("/restaurants/1")
                        .header("x-user-id", 200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRestaurantRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("updated-restaurant-name"))
                .andExpect(jsonPath("$.address.street").value("updated-street"))
                .andExpect(jsonPath("$.address.neighborhood").value("updated-neighborhood"))
                .andExpect(jsonPath("$.address.city").value("updated-city"))
                .andExpect(jsonPath("$.kitchenType").value("MEXICAN"))
                .andExpect(jsonPath("$.openingHours").value("updated-opening-hours"));
    }

    @Test
    @Sql(scripts = {"/sql/restaurant/clean-up.sql",
            "/sql/restaurant/restaurant-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void patch_shouldThrowRestaurantNotFoundException_whenRestaurantNotFound() throws Exception {
        UpdateRestaurantRequest updateRestaurantRequest = RestaurantHelper.buildUpdateRestaurantRequest();

        mockMvc.perform(patch("/restaurants/999")
                    .header("x-user-id", 200)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateRestaurantRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Restaurant not found"));
    }

    @Test
    @Sql(scripts = {"/sql/restaurant/clean-up.sql",
            "/sql/restaurant/restaurant-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void patch_shouldThrowUnauthorizedOperationException_whenUserIsNotOwner() throws Exception {
        UpdateRestaurantRequest updateRestaurantRequest = RestaurantHelper.buildUpdateRestaurantRequest();

        mockMvc.perform(patch("/restaurants/1")
                        .header("x-user-id", 201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRestaurantRequest)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.detail").value("User not authorized to update this restaurant"));
    }

    @Test
    @Sql(scripts = {"/sql/restaurant/clean-up.sql",
            "/sql/restaurant/restaurant-setup.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void patch_shouldUpdatedOnlyOpeningHours_whenOnlyOpeningHoursIsProvided() throws Exception {
        UpdateRestaurantRequest partialUpdateRequest = new UpdateRestaurantRequest(null,
                null,
                null,
                "updated-opening-hours");

        mockMvc.perform(patch("/restaurants/1")
                    .header("x-user-id", 200)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(partialUpdateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("any-restaurant-name"))
                .andExpect(jsonPath("$.address.street").value("any-street-name"))
                .andExpect(jsonPath("$.address.neighborhood").value("any-neighborhood"))
                .andExpect(jsonPath("$.address.city").value("any-city"))
                .andExpect(jsonPath("$.kitchenType").value("BRAZILIAN"))
                .andExpect(jsonPath("$.openingHours").value("updated-opening-hours"));
    }

    @Test
    @Sql(scripts = {"/sql/restaurant/clean-up.sql",
            "/sql/restaurant/restaurant-setup.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void patch_shouldThrowDomainException_whenKitchenTypeIsNotValid() throws Exception {
        UpdateRestaurantRequest updateRestaurantRequest = new UpdateRestaurantRequest("updated-restaurant-name",
                new AddressJson(
                        null,
                        "updated-street",
                        "updated-number",
                        "updated-neighborhood",
                        "updated-city",
                        "updated-zip-code"
                ),
                "Invalid-kitchen-type",
                "updated-opening-hours");

        mockMvc.perform(patch("/restaurants/1")
                .header("x-user-id", 200)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRestaurantRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Invalid kitchen type value: Invalid-kitchen-type"));
    }
}


