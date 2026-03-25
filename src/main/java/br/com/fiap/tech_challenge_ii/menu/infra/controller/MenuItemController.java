package br.com.fiap.tech_challenge_ii.menu.infra.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tech_challenge_ii.menu.core.dto.MenuItemDTO;
import br.com.fiap.tech_challenge_ii.menu.core.usecase.CreateMenuItemUseCase;
import br.com.fiap.tech_challenge_ii.menu.core.usecase.DeleteMenuItemUseCase;
import br.com.fiap.tech_challenge_ii.menu.core.usecase.FindMenuItemUseCase;
import br.com.fiap.tech_challenge_ii.menu.core.usecase.UpdateMenuItemUseCase;
import br.com.fiap.tech_challenge_ii.menu.infra.controller.dto.MenuItemRequestDTO;
import br.com.fiap.tech_challenge_ii.menu.infra.controller.dto.UpdateMenuItemRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuItemController {

    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final FindMenuItemUseCase findMenuItemUseCase;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;

    @PostMapping("/{restaurantId}")
    public ResponseEntity<Map<String, List<Long>>> createMenuItems(
            @RequestHeader("x-user-id") Long userId,
            @PathVariable Long restaurantId,
            @Valid @RequestBody List<MenuItemRequestDTO> menuItems) {

        List<MenuItemDTO> menuItemDTOs = menuItems.stream()
                .map(req -> new MenuItemDTO(
                        req.name(),
                        req.description(),
                        req.price(),
                        req.isOnlyLocalConsuption(),
                        req.photoPath(),
                        restaurantId))
                .toList();

        List<Long> ids = createMenuItemUseCase.save(menuItemDTOs, userId);

        URI uri = URI.create("/menu");
        return ResponseEntity.created(uri).body(Map.of("ids", ids));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
        List<MenuItemDTO> menuItems = findMenuItemUseCase.findAll();
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<MenuItemDTO>> getMenuByRestaurant(
            @PathVariable Long restaurantId) {

        List<MenuItemDTO> byRestaurantId = findMenuItemUseCase.findByRestaurantId(restaurantId);

        return ResponseEntity.ok(byRestaurantId);

    }

    @DeleteMapping("/{restaurantId}/{menuItemId}")
    public ResponseEntity<?> deleteMenuItem(
            @RequestHeader("x-user-id") Long userId,
            @PathVariable Long menuItemId) {

        deleteMenuItemUseCase.delete(menuItemId, userId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restaurantId}/{menuItemId}")
    public ResponseEntity<MenuItemDTO> updateMenuItem(
            @RequestHeader("x-user-id") Long userId,
            @PathVariable Long menuItemId,
            @Valid @RequestBody UpdateMenuItemRequestDTO request) {

        MenuItemDTO dto = new MenuItemDTO(
                request.name(),
                request.description(),
                request.price(),
                request.isOnlyLocalConsuption(),
                request.photoPath(),
                null);

        MenuItemDTO updated = updateMenuItemUseCase.update(menuItemId, dto, userId);

        return ResponseEntity.ok(updated);
    }

}
