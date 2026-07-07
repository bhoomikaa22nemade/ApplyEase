package com.applyease.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "*")
public class ResumeController {

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadResume(
            @RequestParam("resume") MultipartFile file) {

        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {

            response.put("message", "Please select a resume.");

            return ResponseEntity.badRequest().body(response);

        }

        response.put("message", "Resume uploaded successfully!");

        response.put("fileName", file.getOriginalFilename());

        return ResponseEntity.ok(response);

    }

}