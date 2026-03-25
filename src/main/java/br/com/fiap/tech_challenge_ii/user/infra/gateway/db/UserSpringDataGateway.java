package br.com.fiap.tech_challenge_ii.user.infra.gateway.db;

import java.util.List;
import java.util.Optional;

import br.com.fiap.tech_challenge_ii.user.core.dto.CreateUserDTO;
import br.com.fiap.tech_challenge_ii.user.infra.gateway.db.entity.UserEntity;
import org.springframework.stereotype.Component;

import br.com.fiap.tech_challenge_ii.user.core.exception.UserNotFoundException;
import br.com.fiap.tech_challenge_ii.user.infra.exception.UserTypeNotFoundException;
import br.com.fiap.tech_challenge_ii.user.core.domain.Client;
import br.com.fiap.tech_challenge_ii.user.core.domain.Owner;
import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import br.com.fiap.tech_challenge_ii.user.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.user.infra.gateway.db.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;

@Component("userEntitySpringDataGateway")
@RequiredArgsConstructor
public class UserSpringDataGateway implements UserGateway {

    private final UserEntityRepository userEntityRepository;

    @Override
    public Optional<User> findById(Long loggedInUserId) {

        var userEntityOp = userEntityRepository.findById(loggedInUserId);

        if (userEntityOp.isPresent()) {
            var userEntity = userEntityOp.get();
            var name = userEntity.getName();
            var nameType = userEntity.getType();
            var email = userEntity.getEmail();

            if (userEntity.isOwner()) {
                return Optional.of(new Owner(loggedInUserId, name, nameType, email, null));
            } else {
                return Optional.of(new Client(loggedInUserId, name, nameType, email));
            }
        }

        return Optional.empty();
    }

    @Override
    public void update(User updatedUser) {
        var userEntity = userEntityRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new UserNotFoundException());
        userEntity.setName(updatedUser.getName());
        userEntity.setType(updatedUser.getUserType());
        userEntityRepository.save(userEntity);
    }

    @Override
    public void delete(Long id) {
        var userId = userEntityRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        userEntityRepository.delete(userId);
    }

    @Override
    public User save(User user) {
        // Cria entidade diretamente do domain
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setType(user.getUserType());

        UserEntity saved = userEntityRepository.save(entity);

        // Reconstrói domain da entidade salva
        return switch (saved.getType()) {
            case "OWNER" -> Owner.builder()
                    .id(saved.getId())
                    .name(saved.getName())
                    .email(saved.getEmail())
                    .userType(saved.getType())
                    .build();
            case "CLIENT" -> Client.builder()
                    .id(saved.getId())
                    .name(saved.getName())
                    .email(saved.getEmail())
                    .userType(saved.getType())
                    .build();
            default -> throw new UserTypeNotFoundException();
        };
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userEntityRepository.existsByEmail(email);
    }

    @Override
    public User createFromDTO(CreateUserDTO dto) {

        return switch (dto.type().toUpperCase()) {
            case "OWNER" -> Owner.builder()
                    .name(dto.name())
                    .email(dto.email())
                    .userType(dto.type().toUpperCase())
                    .build();
            case "CLIENT" -> Client.builder()
                    .name(dto.name())
                    .email(dto.email())
                    .userType(dto.type().toUpperCase())
                    .build();
            default -> throw new UserTypeNotFoundException();
        };
    }

    @Override
    public List<User> findAll() {
        return userEntityRepository.findAll().stream().map(this::toDomain).toList();
    }

    private User toDomain(UserEntity entity) {
        return switch (entity.getType()) {
            case "OWNER" -> Owner.builder()
                    .id(entity.getId()).name(entity.getName())
                    .email(entity.getEmail()).userType(entity.getType())
                    .build();
            case "CLIENT" -> Client.builder()
                    .id(entity.getId()).name(entity.getName())
                    .email(entity.getEmail()).userType(entity.getType())
                    .build();
            default -> throw new UserTypeNotFoundException();
        };
    }

}
