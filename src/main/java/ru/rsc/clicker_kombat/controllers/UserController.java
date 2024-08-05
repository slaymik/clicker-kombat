package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.responses.UserResponse;
import ru.rsc.clicker_kombat.services.UserService;

import java.util.Map;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUser(@RequestParam("tg_id") String id) {
        return ResponseEntity.ok(userService.getUserOrError(Long.parseLong(id)));
    }

    @PostMapping("/auth")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserResponse response) {
        return ResponseEntity.ok(userService.createUser(response));
    }
}