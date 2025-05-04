package com.example.forkcount.controller;

import com.example.forkcount.service.WordCountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/wordcount")
@Tag(name = "Word Count API", description = "Count word occurrences in text files using ForkJoinPool")
public class WordCountController {

    private final WordCountService wordCountService;

    public WordCountController(WordCountService wordCountService) {
        this.wordCountService = wordCountService;
    }

    @GetMapping
    @Operation(summary = "Count word occurrences", description = "Counts how many times a word appears in all .txt files inside a given folder")
    public ResponseEntity<String> count(
            @Parameter(description = "Path to root folder", example = "/Users/you/docs")
            @RequestParam String path,

            @Parameter(description = "Word to count", example = "java")
            @RequestParam String word) {

        try {
            int count = wordCountService.countWordOccurrences(path, word);
            return ResponseEntity.ok("{\"word\":\"" + word + "\", \"count\":" + count + "}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}







