package br.com.fiap.tech_challenge_ii.user.core.usecase;

import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import br.com.fiap.tech_challenge_ii.user.core.exception.UserNotFoundException;
import br.com.fiap.tech_challenge_ii.user.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.user.core.usecase.impl.DeleteUserUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private DeleteUserUseCaseImpl useCase;

    @Test
    void deveDeletarUsuarioQuandoExistir() {
        // Arrange
        Long userId = 1L;
        User user = mock(User.class);
        when(user.getId()).thenReturn(userId);
        when(userGateway.findById(userId)).thenReturn(Optional.of(user));

        // Act
        useCase.delete(userId);

        // Assert
        verify(userGateway).findById(userId);
        verify(userGateway).delete(userId);
        verifyNoMoreInteractions(userGateway);
    }

    @Test
    void deveLancarUserNotFoundExceptionQuandoUsuarioNaoExistir() {
        // Arrange
        Long userId = 1L;
        when(userGateway.findById(userId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(UserNotFoundException.class, () -> useCase.delete(userId));

        verify(userGateway).findById(userId);
        verify(userGateway, never()).delete(anyLong());
        verifyNoMoreInteractions(userGateway);
    }
}

