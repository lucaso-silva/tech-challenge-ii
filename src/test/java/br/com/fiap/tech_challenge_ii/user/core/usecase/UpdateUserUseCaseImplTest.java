package br.com.fiap.tech_challenge_ii.user.core.usecase;

import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import br.com.fiap.tech_challenge_ii.user.core.dto.UpdateUserDTO;
import br.com.fiap.tech_challenge_ii.user.core.exception.UserNotFoundException;
import br.com.fiap.tech_challenge_ii.user.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.user.core.usecase.impl.UpdateUserUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private User user;

    @InjectMocks
    private UpdateUserUseCaseImpl useCase;

    @Test
    void deveAtualizarUsuarioQuandoExistir() {

        Long loggedInUserId = 1L;
        UpdateUserDTO input = new UpdateUserDTO("Novo Nome", "OWNER");

        when(userGateway.findById(loggedInUserId)).thenReturn(Optional.of(user));


        useCase.update(loggedInUserId, input);


        verify(userGateway).findById(loggedInUserId);
        verify(user).update(input.name(), input.userType());
        verify(userGateway).update(user);
        verifyNoMoreInteractions(userGateway);
    }

    @Test
    void deveLancarUserNotFoundExceptionQuandoUsuarioNaoExistir() {
        // Arrange
        Long loggedInUserId = 1L;
        UpdateUserDTO input = new UpdateUserDTO("Novo Nome", "OWNER");

        when(userGateway.findById(loggedInUserId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(UserNotFoundException.class,
                () -> useCase.update(loggedInUserId, input));

        verify(userGateway).findById(loggedInUserId);
        verify(userGateway, never()).update(any());
        verifyNoInteractions(user);
    }
}
