package com.example.labeling.controller;

import com.example.labeling.dto.LabelRequest;
import com.example.labeling.dto.ParagraphRequest;
import com.example.labeling.model.Sentence;
import com.example.labeling.service.LabelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React frontend
public class LabelingController {

    private final LabelingService labelingService;

    @Autowired
    public LabelingController(LabelingService labelingService) {
        this.labelingService = labelingService;
    }

    @PostMapping("/admin/sentences")
    public ResponseEntity<Map<String, Object>> processParagraph(@RequestBody ParagraphRequest request) {
        List<String> sentences = labelingService.processParagraph(request.getParagraph());
        return ResponseEntity.ok(Map.of(
            "message", "Sentences processed successfully",
            "count", sentences.size(),
            "sentences", sentences
        ));
    }

    @GetMapping("/random-text")
    public ResponseEntity<Map<String, Object>> getNextSentence() {
        Sentence sentence = labelingService.getNextSentence();
        if (sentence == null) {
            return ResponseEntity.ok(Map.of(
                "message", "No sentences available"
            ));
        }
        return ResponseEntity.ok(Map.of(
            "id", sentence.getTimestamp().toString(),
            "text", sentence.getText()
        ));
    }

    @PostMapping("/label")
    public ResponseEntity<Map<String, String>> labelSentence(@RequestBody LabelRequest request) {
        labelingService.labelSentence(request.getText(), request.getCategory());
        return ResponseEntity.ok(Map.of(
            "message", "Sentence labeled successfully"
        ));
    }

    @PostMapping("/label-user-input")
    public ResponseEntity<Map<String, String>> labelUserInput(@RequestBody LabelRequest request) {
        labelingService.labelSentence(request.getText(), request.getCategory());
        return ResponseEntity.ok(Map.of(
            "message", "User input labeled successfully"
        ));
    }

    @GetMapping("/unlabeled")
    public ResponseEntity<List<Sentence>> getUnlabeledSentences() {
        return ResponseEntity.ok(labelingService.getUnlabeledSentences());
    }
} 