package com.example.labeling.model;

import java.time.LocalDateTime;

public class Sentence {
    private String text;
    private String label;
    private LocalDateTime timestamp;
    private boolean isLabeled;

    public Sentence() {
    }

    public Sentence(String text) {
        this.text = text;
        this.isLabeled = false;
        this.timestamp = LocalDateTime.now();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        this.isLabeled = true;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isLabeled() {
        return isLabeled;
    }

    public void setLabeled(boolean labeled) {
        isLabeled = labeled;
    }
} 