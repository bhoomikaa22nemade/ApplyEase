package com.applyease.backend.controller;

import com.applyease.backend.dto.JobDTO;
import com.applyease.backend.dto.JobRequest;
import com.applyease.backend.service.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/search")
    public Map<String, List<JobDTO>> searchJobs(
            @RequestBody JobRequest request) {

        List<JobDTO> jobs = jobService.searchJobs(request);

        Map<String, List<JobDTO>> response = new HashMap<>();

        response.put("jobs", jobs);

        return response;
    }

}
