package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @GetMapping("/public")
    public ResponseEntity<String> getPublicInfo() {
        // language=json
        return ResponseEntity.ok("""
                {
                    "appTitle": "Course Manager"
                }
                """);
    }

}
