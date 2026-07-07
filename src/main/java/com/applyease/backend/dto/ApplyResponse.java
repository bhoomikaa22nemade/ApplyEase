package com.applyease.backend.dto;

import java.util.List;

public class ApplyResponse {

    private int applied;

    private List<ApplyResult> results;

    public ApplyResponse() {
    }

    public ApplyResponse(int applied, List<ApplyResult> results) {

        this.applied = applied;
        this.results = results;

    }

    public int getApplied() {
        return applied;
    }

    public void setApplied(int applied) {
        this.applied = applied;
    }

    public List<ApplyResult> getResults() {
        return results;
    }

    public void setResults(List<ApplyResult> results) {
        this.results = results;
    }

}
