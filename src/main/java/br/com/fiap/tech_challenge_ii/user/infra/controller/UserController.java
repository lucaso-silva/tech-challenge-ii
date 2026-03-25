package br.com.fiap.tech_challenge_ii.user.infra.controller;

import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import br.com.fiap.tech_challenge_ii.user.core.dto.CreateUserDTO;
import br.com.fiap.tech_challenge_ii.user.core.dto.UpdateUserDTO;
import br.com.fiap.tech_challenge_ii.user.core.usecase.CreateUserUseCase;
import br.com.fiap.tech_challenge_ii.user.core.usecase.GetUserUseCase;
import br.com.fiap.tech_challenge_ii.user.core.usecase.UpdateUserUseCase;
import br.com.fiap.tech_challenge_ii.user.core.usecase.DeleteUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;


    @PostMapping
    public ResponseEntity<User> create(@RequestBody CreateUserDTO dto) {
        User created = createUserUseCase.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = getUserUseCase.findAll();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = getUserUseCase.findById(id);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody UpdateUserDTO dto) {
        updateUserUseCase.update(id, dto);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUserUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
