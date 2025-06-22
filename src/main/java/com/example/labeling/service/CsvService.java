package com.example.labeling.service;

import com.example.labeling.model.Sentence;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class CsvService {

    @Value("${app.csv.file-path:labeled_data.csv}")
    private String csvFilePath;

    public void initializeCsvFile() {
        Path path = Paths.get(csvFilePath);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
                // Write header
                FileWriter writer = new FileWriter(csvFilePath);
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
                csvPrinter.close();
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize CSV file", e);
            }
        }
    }

    public void appendToCSV(Sentence sentence) {
        try (FileWriter fileWriter = new FileWriter(csvFilePath, true);
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
            
            // Write the record with proper escaping
            csvPrinter.printRecord(
                sentence.getText(),
                sentence.getLabel()
            );
            
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to append to CSV file", e);
        }
    }
} 