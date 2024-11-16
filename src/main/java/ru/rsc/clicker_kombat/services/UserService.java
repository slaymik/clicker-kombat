package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.UserCredentials;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public ActionResult registerUser(UserCredentials userCredentials) {
        String username = userCredentials.getUsername();
        if (isUserExists(username)) {
            return new ActionResult(false, "Пользователь с username: %s уже существует".formatted(username));
        }

        userDetailsManager.createUser(User.builder()
                .passwordEncoder(passwordEncoder::encode)
                .username(username)
                .password(userCredentials.getPassword())
                .roles(userCredentials.getRole())
                .build());

        return isUserExists(username) ? new ActionResult(true, "Пользователь c username: %s успешно создан".formatted(username))
                : new ActionResult(false, "Во время создания пользователя произошла ошибка");
    }

    public Optional<UserCredentials> getUserByUsername(String username){
        if(!isUserExists(username)){
            return Optional.empty();
        }
        return Optional.of(UserCredentials.fromUserDetails(userDetailsManager.loadUserByUsername(username)));
    }

    private boolean isUserExists(String username) {
        return userDetailsManager.userExists(username);
    }
}