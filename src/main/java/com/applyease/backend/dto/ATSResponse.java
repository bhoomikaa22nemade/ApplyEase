package com.applyease.backend.dto;



import java.util.List;

public class ATSResponse {

    private int score;
    private List<String> strengths;
    private List<String> weaknesses;
    private List<String> missingSkills;
    private List<String> suggestions;

    public ATSResponse() {
    }

    public ATSResponse(int score,
                       List<String> strengths,
                       List<String> weaknesses,
                       List<String> missingSkills,
                       List<String> suggestions) {

        this.score = score;
        this.strengths = strengths;
        this.weaknesses = weaknesses;
        this.missingSkills = missingSkills;
        this.suggestions = suggestions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

}
