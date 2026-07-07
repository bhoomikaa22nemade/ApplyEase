package com.applyease.backend.controller;

import com.applyease.backend.dto.ApplyRequest;
import com.applyease.backend.dto.ApplyResponse;
import com.applyease.backend.service.AutomationService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class AutomationController {

    @Autowired
    private AutomationService automationService;

    @PostMapping("/apply")
    public ApplyResponse applyJobs(
            @RequestBody ApplyRequest request) {

        return automationService.applyJobs(request);

    }

}
