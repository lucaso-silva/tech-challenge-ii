package br.com.fiap.tech_challenge_ii.user.core.usecase;

import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import br.com.fiap.tech_challenge_ii.user.core.exception.UserNotFoundException;
import br.com.fiap.tech_challenge_ii.user.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.user.core.usecase.impl.GetUserUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private GetUserUseCaseImpl useCase;

    @Test
    void deveRetornarTodosUsuarios() {
        List<User> users = List.of(mock(User.class), mock(User.class));
        when(userGateway.findAll()).thenReturn(users);

        List<User> result = useCase.findAll();

        assertEquals(2, result.size());
        verify(userGateway).findAll();
    }

    @Test
    void deveRetornarUsuarioPorId() {
        User user = mock(User.class);
        when(userGateway.findById(1L)).thenReturn(Optional.of(user));

        User result = useCase.findById(1L);

        assertEquals(user, result);
        verify(userGateway).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(userGateway.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> useCase.findById(1L));
        verify(userGateway).findById(1L);
    }
}
