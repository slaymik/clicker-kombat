package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.User;
import ru.rsc.clicker_kombat.model.responses.UserResponse;
import ru.rsc.clicker_kombat.repository.UserRepository;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Map<String,Object> getUserOrError(Long id) {
        Map<String,Object> map = new HashMap<>();
        if(userRepository.findById(id).isPresent())
            map.put("User",userRepository.findById(id));
        else
            map.put("Error","Пользователь с id:%s не найден".formatted(id));
        return map;
    }

    public Map<String,Object> createUser(UserResponse response) {
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
        return getUserOrError(response.getId());
    }
}