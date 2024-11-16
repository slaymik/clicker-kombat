//package ru.rsc.clicker_kombat.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import ru.rsc.clicker_kombat.model.domain.Player;
//import ru.rsc.clicker_kombat.model.requests.UserCredentialsRequest;
//import ru.rsc.clicker_kombat.model.requests.FactionRequest;
//import ru.rsc.clicker_kombat.model.responses.ActionResult;
//import ru.rsc.clicker_kombat.model.responses.EntityResponse;
//import ru.rsc.clicker_kombat.model.responses.LeaderboardResponse;
//import ru.rsc.clicker_kombat.services.*;
//
//import java.util.List;
//
//@RestController
//@PreAuthorize("hasRole('ADMIN')")
//@RequestMapping("/admin")
//@RequiredArgsConstructor
//public class AdminController {
//    private final AdminService adminService;
//    private final PlayerService playerService;
//    private final CharacterService characterService;
//    private final FactionService factionService;
//    private final CharacterInfoService characterInfoService;
//
//    @GetMapping
//    public ResponseEntity<List<String>> getAllAdmins() {
//        return ResponseEntity.ok(adminService.getAllAdmins());
//    }
//
//    @GetMapping("/get")
//    public ResponseEntity<EntityResponse> getUserById(@RequestParam("id") String id) {
//        return ResponseEntity.ok(playerService.getUser(Long.parseLong(id)));
//    }
//
//    @GetMapping("/delete")
//    public ActionResult deleteCharacter(@RequestParam("id") String id){
//        return characterService.deleteCharacter(Long.parseLong(id));
//    }
//
//    @GetMapping("/get-all")
//    public List<Player> getAllUsers() {
//        return playerService.getAllUsers();
//    }
//
//    @GetMapping("/create-admin")
//    public ActionResult createAdmin(@RequestBody UserCredentialsRequest request){
//        return adminService.createAdmin(request);
//    }
//
//    @GetMapping("/create-faction")
//    public ResponseEntity<EntityResponse> createFaction(@RequestBody FactionRequest request){
//        return ResponseEntity.ok(factionService.createFaction(request));
//    }
//
//    @GetMapping("/get-faction")
//    public ResponseEntity<EntityResponse> getFaction(@RequestParam("id") String id){
//        return ResponseEntity.ok(factionService.getFactionResponse(Long.parseLong(id)));
//    }
//
//    @GetMapping("/set-faction")
//    public ResponseEntity<EntityResponse> setFaction(@RequestParam("id") String id){
//        return ResponseEntity.ok(factionService.setFactionToInactive(Long.parseLong(id)));
//    }
//
//    @GetMapping("/delete-admin")
//    public ActionResult deleteAdmin(@RequestParam("username") String username){
//        return adminService.deleteAdmin(username);
//    }
//
//    @GetMapping("/peak-leaderboard")
//    public List<LeaderboardResponse> getPeakLevelLeaderboard(){
//        return characterInfoService.getPeakLevelLeaderboard();
//    }
//
//    @GetMapping("/profit-leaderboard")
//    public List<LeaderboardResponse> getProfitLevelLeaderboard(){
//        return characterInfoService.getProfitLeaderboard();
//    }
//}