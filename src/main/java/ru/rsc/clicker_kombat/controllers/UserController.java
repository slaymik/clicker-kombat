package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.requests.UserRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.UserService;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<EntityResponse> getUser(@RequestParam("tg_id") String id) {
        return ResponseEntity.ok(userService.getUser(Long.parseLong(id)));
    }

    @PostMapping("/auth")
    public ResponseEntity<EntityResponse> createUser(@RequestBody UserRequest response) {
        return ResponseEntity.ok(userService.createUser(response));
    }
}