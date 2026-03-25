package br.com.fiap.tech_challenge_ii.user.core.usecase;

import br.com.fiap.tech_challenge_ii.user.core.domain.Owner;
import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import br.com.fiap.tech_challenge_ii.user.core.dto.CreateUserDTO;
import br.com.fiap.tech_challenge_ii.user.core.exception.UserAlreadyExistsException;
import br.com.fiap.tech_challenge_ii.user.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.user.core.usecase.impl.CreateUserUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private CreateUserUseCaseImpl useCase;

    @Test
    void deveCriarUsuarioQuandoEmailNaoExiste() {
        // Arrange
        CreateUserDTO dto = new CreateUserDTO("joao@teste.com", "João", "OWNER");
        User newUser = new Owner(1L,"João", "OWNER", null);

        when(userGateway.existsByEmail(dto.email())).thenReturn(false);
        when(userGateway.createFromDTO(dto)).thenReturn(newUser);
        when(userGateway.save(newUser)).thenReturn(newUser);

        // Act
        User result = useCase.create(dto);

        // Assert
        assertEquals(1L, result.getId());
        verify(userGateway).existsByEmail(dto.email());
        verify(userGateway).createFromDTO(dto);
        verify(userGateway).save(newUser);
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExiste() {
        // Arrange
        CreateUserDTO dto = new CreateUserDTO("joao@teste.com", "João", "OWNER");
        when(userGateway.existsByEmail(dto.email())).thenReturn(true);

        // Act + Assert
        assertThrows(UserAlreadyExistsException.class, () -> useCase.create(dto));
        verify(userGateway).existsByEmail(dto.email());
        verifyNoMoreInteractions(userGateway);
    }
}
