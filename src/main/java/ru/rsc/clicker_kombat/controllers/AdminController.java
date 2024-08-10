package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.domain.User;
import ru.rsc.clicker_kombat.model.requests.AdminRequest;
import ru.rsc.clicker_kombat.model.requests.FactionRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.services.AdminService;
import ru.rsc.clicker_kombat.services.CharacterService;
import ru.rsc.clicker_kombat.services.FactionService;
import ru.rsc.clicker_kombat.services.UserService;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;
    private final CharacterService characterService;
    private final FactionService factionService;

    @GetMapping
    public ResponseEntity<List<String>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/get")
    public ResponseEntity<EntityResponse> getUserById(@RequestParam("id") String id) {
        return ResponseEntity.ok(userService.getUser(Long.parseLong(id)));
    }

    @GetMapping("/delete")
    public ActionResult deleteCharacter(@RequestParam("id") String id){
        return characterService.deleteCharacter(Long.parseLong(id));
    }

    @GetMapping("/get-all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/create-admin")
    public ActionResult createAdmin(@RequestBody AdminRequest request){
        return adminService.createAdmin(request);
    }

    @GetMapping("/create-faction")
    public ResponseEntity<EntityResponse> createFaction(@RequestBody FactionRequest request){
        return ResponseEntity.ok(factionService.createFaction(request));
    }

    @GetMapping("/get-faction")
    public ResponseEntity<EntityResponse> getFaction(@RequestParam("id") String id){
        return ResponseEntity.ok(factionService.getFactionResponse(Long.parseLong(id)));
    }

    @GetMapping("/set-faction")
    public ResponseEntity<EntityResponse> setFaction(@RequestParam("id") String id){
        return ResponseEntity.ok(factionService.setFactionToInactive(Long.parseLong(id)));
    }

    @GetMapping("/delete-admin")
    public ActionResult deleteAdmin(@RequestParam("username") String username){
        return adminService.deleteAdmin(username);
    }
}