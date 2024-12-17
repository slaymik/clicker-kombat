package ru.rsc.clicker_kombat.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("/version")
public class VersionController {
    @Value("${version.path}")
    private String currentVersionFilePath;

    @GetMapping("")
    public ResponseEntity<?> getCurrentVersion() throws IOException {
        String version = Files.readString(Path.of(currentVersionFilePath));
        return ResponseEntity.ok(Map.of("version", version));
    }
}
