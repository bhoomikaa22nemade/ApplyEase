package com.applyease.backend.service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.applyease.backend.dto.ATSResponse;

@Service
public class ATSService {

    // Common resume action verbs used to check whether the candidate
    // describes their work with strong, specific language.
    private static final String[] ACTION_VERBS = {
            "developed", "implemented", "designed", "managed", "led",
            "built", "created", "optimized", "improved", "launched",
            "architected", "migrated", "automated", "deployed", "delivered"
    };

    public ATSResponse analyzeResume(MultipartFile file) throws Exception {

        String rawText = extractText(file);
        String text = rawText.toLowerCase();

        if (text.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Could not read any text from this file. Please upload a text-based PDF or DOCX (not a scanned image).");
        }

        int score = 0;

        List<String> strengths = new ArrayList<>();
        List<String> weaknesses = new ArrayList<>();
        List<String> missing = new ArrayList<>();
        List<String> suggestions = new ArrayList<>();

        // ---- Technical skills (78 points total) ----
        // Word-boundary matching avoids false positives like "git" matching
        // inside "digital", or "rest" matching inside "interested"/"forest".
        score += check(text, "java", 15, strengths, missing, "Java");
        score += check(text, "spring", 15, strengths, missing, "Spring Boot");
        score += check(text, "mysql", 8, strengths, missing, "MySQL");
        score += check(text, "rest", 8, strengths, missing, "REST API");
        score += check(text, "hibernate", 8, strengths, missing, "Hibernate");
        score += check(text, "react", 8, strengths, missing, "React");
        score += check(text, "javascript", 4, strengths, missing, "JavaScript");
        score += check(text, "html", 4, strengths, missing, "HTML");
        score += check(text, "css", 4, strengths, missing, "CSS");
        score += check(text, "git", 4, strengths, missing, "Git");

        for (String skill : missing) {
            suggestions.add("Add experience or mention of " + skill);
        }

        // ---- Resume quality signals (22 points total) ----

        // Contact info
        if (matches(text, "\\b[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}\\b")) {
            score += 5;
            strengths.add("Email address included");
        } else {
            weaknesses.add("No email address found");
            suggestions.add("Add a professional email address so recruiters can reach you");
        }

        if (matches(text, "(\\+?\\d{1,3}[-.\\s]?)?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}")) {
            score += 3;
            strengths.add("Phone number included");
        } else {
            weaknesses.add("No phone number found");
            suggestions.add("Add a contact phone number");
        }

        // Quantifiable achievements (numbers/percentages showing real impact)
        if (matches(text, "\\d+\\s?%") || matches(text, "\\$\\s?\\d+") || matches(text, "\\b\\d{2,}\\b")) {
            score += 6;
            strengths.add("Includes quantifiable achievements (numbers/metrics)");
        } else {
            weaknesses.add("No quantifiable achievements found");
            suggestions.add("Add measurable results, e.g. \"reduced load time by 30%\" or \"managed a team of 5\"");
        }

        // Action verbs
        int verbCount = 0;
        for (String verb : ACTION_VERBS) {
            if (matches(text, "\\b" + verb + "\\b")) {
                verbCount++;
            }
        }
        if (verbCount >= 3) {
            score += 4;
            strengths.add("Uses strong action verbs (e.g. developed, led, built)");
        } else {
            weaknesses.add("Limited use of strong action verbs");
            suggestions.add("Start bullet points with action verbs like \"Led\", \"Built\", or \"Optimized\"");
        }

        // Core resume sections
        int sectionCount = 0;
        for (String section : new String[]{"experience", "education", "skills"}) {
            if (matches(text, "\\b" + section + "\\b")) {
                sectionCount++;
            }
        }
        if (sectionCount >= 2) {
            score += 4;
            strengths.add("Well-structured with clear sections (experience/education/skills)");
        } else {
            weaknesses.add("Missing clear section headers (Experience, Education, Skills)");
            suggestions.add("Use clear section headers so ATS software can parse your resume correctly");
        }

        if (score > 100) {
            score = 100;
        }

        if (score >= 80) {
            strengths.add(0, "Strong overall resume for ATS scanning");
        } else if (score >= 60) {
            strengths.add(0, "Decent resume, but room to improve");
        } else {
            weaknesses.add(0, "Resume needs significant improvement to pass ATS screening");
        }

        return new ATSResponse(
                score,
                strengths,
                weaknesses,
                missing,
                suggestions
        );
    }

    private int check(String text,
                       String keyword,
                       int points,
                       List<String> strengths,
                       List<String> missing,
                       String display) {

        if (matches(text, "\\b" + Pattern.quote(keyword) + "\\b")) {
            strengths.add(display + " mentioned");
            return points;
        }

        missing.add(display);
        return 0;
    }

    private boolean matches(String text, String regex) {
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(text).find();
    }

    private String extractText(MultipartFile file) throws Exception {

        String name = file.getOriginalFilename();

        if (name == null) {
            return "";
        }

        String lowerName = name.toLowerCase();

        if (lowerName.endsWith(".pdf")) {
            try (PDDocument document = PDDocument.load(file.getInputStream())) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            }
        }

        if (lowerName.endsWith(".docx")) {
            try (InputStream is = file.getInputStream();
                 XWPFDocument doc = new XWPFDocument(is)) {

                StringBuilder sb = new StringBuilder();
                doc.getParagraphs().forEach(p -> sb.append(p.getText()).append(" "));
                return sb.toString();
            }
        }

        if (lowerName.endsWith(".txt")) {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        }

        if (lowerName.endsWith(".doc")) {
            throw new IllegalArgumentException(
                    "Legacy .doc format is not supported. Please upload a .pdf or .docx file.");
        }

        throw new IllegalArgumentException(
                "Unsupported file type. Please upload a .pdf or .docx file.");
    }
}