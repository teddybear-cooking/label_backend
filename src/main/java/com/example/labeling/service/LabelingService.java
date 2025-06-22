package com.example.labeling.service;

import com.example.labeling.model.Sentence;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class LabelingService {

    private final Queue<Sentence> unlabeledSentences = new ConcurrentLinkedQueue<>();
    private final CsvService csvService;

    @Autowired
    public LabelingService(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostConstruct
    public void init() {
        csvService.initializeCsvFile();
    }

    public List<String> processParagraph(String paragraph) {
        // Split paragraph into sentences (simple implementation)
        String[] sentences = paragraph.split("[.!?]+\\s*");
        List<String> processedSentences = new ArrayList<>();
        
        for (String sentence : sentences) {
            sentence = sentence.trim();
            if (!sentence.isEmpty()) {
                unlabeledSentences.add(new Sentence(sentence));
                processedSentences.add(sentence);
            }
        }
        
        return processedSentences;
    }

    public Sentence getNextSentence() {
        return unlabeledSentences.peek();
    }

    public void labelSentence(String text, String category) {
        // Find and remove the sentence from unlabeled queue
        Sentence toLabel = null;
        for (Sentence s : unlabeledSentences) {
            if (s.getText().equals(text)) {
                toLabel = s;
                unlabeledSentences.remove(s);
                break;
            }
        }

        // If sentence wasn't in queue, create new one
        if (toLabel == null) {
            toLabel = new Sentence(text);
        }

        // Set the label and save to CSV
        toLabel.setLabel(category);
        csvService.appendToCSV(toLabel);
    }

    public List<Sentence> getUnlabeledSentences() {
        return new ArrayList<>(unlabeledSentences);
    }
} 