package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.User;
import ru.rsc.clicker_kombat.model.requests.UserRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static ru.rsc.clicker_kombat.consts.EntityResponseStatuses.ERROR;
import static ru.rsc.clicker_kombat.consts.EntityResponseStatuses.SUCCESS;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public EntityResponse getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return EntityResponse.builder()
                    .status(SUCCESS)
                    .entity(user.orElseThrow())
                    .build();
        } else {
            return EntityResponse.builder()
                    .status(ERROR)
                    .message("Пользователь с id:%s не найден".formatted(id))
                    .build();
        }
    }

    public EntityResponse createUser(UserRequest response) {
        User user = User.builder()
                .id(response.getId())
                .username(response.getUsername())
                .token(response.getToken())
                .registrationDate(Instant.now())
                .lastOnline(null)
                .isActive(true)
                .characters(null)
                .build();
        userRepository.save(user);
        return getUser(response.getId());
    }
}