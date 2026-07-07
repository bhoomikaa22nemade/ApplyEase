package com.applyease.backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.applyease.backend.dto.ATSResponse;
import com.applyease.backend.service.ATSService;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "*")
public class ATSController {

    @Autowired
    private ATSService atsService;

    @PostMapping("/check")
    public ResponseEntity<?> checkResume(@RequestParam("resume") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Please upload a resume file."));
        }

        try {
            ATSResponse result = atsService.analyzeResume(file);
            return ResponseEntity.ok(result);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Failed to analyze resume. Please try a different file."));
        }
    }
}