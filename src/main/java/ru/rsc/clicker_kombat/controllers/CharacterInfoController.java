package ru.rsc.clicker_kombat.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsc.clicker_kombat.model.requests.CharacterInfoRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.model.responses.LeaderboardResponse;
import ru.rsc.clicker_kombat.services.CharacterInfoService;

import java.util.List;

@RestController
@RequestMapping("character/info")
@RequiredArgsConstructor
public class CharacterInfoController {
    private final CharacterInfoService characterInfoService;

    @GetMapping
    public ResponseEntity<EntityResponse> getCharacterInfo(@RequestParam("id") String id){
        return ResponseEntity.ok(characterInfoService.getCharacterInfoResponse(Long.parseLong(id)));
    }

    @PostMapping("/update")
    public ResponseEntity<EntityResponse> updateCharacterInfo(@RequestBody CharacterInfoRequest request){
        return ResponseEntity.ok(characterInfoService.updateProfit(request));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardResponse>> getLeaderboard(){
        return ResponseEntity.ok(characterInfoService.getProfitLeaderboard());
    }
}
