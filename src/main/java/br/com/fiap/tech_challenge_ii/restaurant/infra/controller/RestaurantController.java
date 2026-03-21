package br.com.fiap.tech_challenge_ii.restaurant.infra.controller;

import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.filter.RestaurantSearchFilter;
import br.com.fiap.tech_challenge_ii.restaurant.core.usecase.*;
import br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json.*;
import br.com.fiap.tech_challenge_ii.restaurant.infra.controller.mapper.RestaurantControllerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {
    private final CreateRestaurantUseCase createRestaurant;
    private final GetRestaurantByIdUseCase getRestaurantByIdUseCase;
    private final ListRestaurantsUseCase listRestaurantsUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantById;
    private final RestaurantControllerMapper mapper;

    @PostMapping
    public ResponseEntity<CreateRestaurantResponse> create(@RequestHeader("x-user-id") Long userId,
            @Valid @RequestBody CreateRestaurantRequest input){

        var output = createRestaurant.create(userId, mapper.toInput(input));
        URI uri = URI.create("/restaurants/" + output.id());
        return ResponseEntity.created(uri).body(mapper.toResponse(output));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetRestaurantResponse> getById(@PathVariable Long id){
        var restaurant = getRestaurantByIdUseCase.getById(id);

        return ResponseEntity.ok(mapper.toResponse(restaurant));
    }

    @GetMapping
    public ResponseEntity<List<ListRestaurantResponse>> listAll(@RequestParam(name = "name", required = false) String name){
        var filter = new RestaurantSearchFilter(name != null ? name : "");
        var response = listRestaurantsUseCase.list(filter).stream()
                .map(mapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateRestaurantResponse> update(@RequestHeader("x-user-id") Long loggedUserId,
                                                           @PathVariable("id") Long restaurantId,
                                                           @Valid @RequestBody UpdateRestaurantRequest input){

        var updatedRestaurant = updateRestaurantUseCase.update(loggedUserId, restaurantId, mapper.toInput(input));

        return ResponseEntity.ok(mapper.toResponse(updatedRestaurant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@RequestHeader("x-user-id") Long id,
            @PathVariable("id") Long restaurantId){

        deleteRestaurantById.deleteById(id, restaurantId);

        return ResponseEntity.noContent().build();
    }
}

