package com.applyease.backend.service;

import com.applyease.backend.dto.ApplyRequest;
import com.applyease.backend.dto.ApplyResponse;
import com.applyease.backend.dto.ApplyResult;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AutomationService {

    public ApplyResponse applyJobs(ApplyRequest request) {

        List<ApplyResult> results = new ArrayList<>();

        for (String id : request.getJobIds()) {

            results.add(

                new ApplyResult(

                    "Application submitted successfully (Job ID : " + id + ")"

                )

            );

        }

        return new ApplyResponse(

                request.getJobIds().size(),

                results

        );

    }

}
