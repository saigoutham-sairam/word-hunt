package com.example.forkcount.service;

import com.example.forkcount.task.WordCountTask;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WordCountService {

    public int countWordOccurrences(String path, String word) {
        File root = new File(path);
        if (!root.exists() || !root.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory path: " + path);
        }

        AtomicInteger total = new AtomicInteger();
        ForkJoinPool pool = new ForkJoinPool();
        try {
            WordCountTask task = new WordCountTask(root, word.toLowerCase(), total);
            pool.invoke(task);
        } finally {
            pool.shutdown();
        }

        return total.get();
    }
}




