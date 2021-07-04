package com.br.recycle.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.recycle.api.model.User;
import com.br.recycle.api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

//	@Test
//	void shouldSavedUserSuccessFully() {
//		final User user = new User(null, "ten@mail.com", "teten", null, "teten", null, null, null, null, null, null,
//				null, null);
//
//		given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.empty());
//		given(userRepository.save(user)).willAnswer(invocation -> invocation.getArgument(0));
//
//		User savedUser = userService.save(user);
//
//		assertThat(savedUser).isNotNull();
//
//		verify(userRepository).save(any(User.class));
//
//	}

	@Test
	void findUserById() {
		final Long id = 1L;
		final User user = new User(1L, "ten@mail.com", "teten", null, "teten", null, null, null, null, null, null, null,null);

		given(userRepository.findById(id)).willReturn(Optional.of(user));

		User expected  = userService.fetchOrFail(id);

		assertThat(expected).isNotNull();

	}
	

//    @Test
//    void updateUser() {
//        final User user = new User(1L, "ten@mail.com","teten",null, "teten", null, null, null, null, null, null, null, null);
//
//        given(userRepository.save(user)).willReturn(user);
//
//        final User expected = userService.update(user);
//
//        assertThat(expected).isNotNull();
//
//        verify(userRepository).save(any(User.class));
//    }
//    
//    @Test
//    void shouldThrowErrorWhenSaveUserWithExistingEmail() {
//        final User user = new User(1L, "teste","ten@mail.com",RoleName.ROLE_ADMIN, "teten", "teten", "11999999999", "59517244061", Flow.A, null, null, null, null);
//
//        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));
//
//        assertThrows(UserNotFoundException.class,() -> {
//            userService.save(user);
//        });
//
//        verify(userRepository, never()).save(any(User.class));
//    }

}
