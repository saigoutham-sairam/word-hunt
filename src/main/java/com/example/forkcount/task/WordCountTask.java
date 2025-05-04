package com.example.forkcount.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public
class WordCountTask extends RecursiveAction {

    private final File directory;
    private final String word;
    private final AtomicInteger totalCount;

    public
    WordCountTask(File root, String lowerCase, AtomicInteger total) {
        this.directory = root;
        this.word = lowerCase;
        this.totalCount = total;
    }

    @Override
    protected void compute() {
        //Initialize a list to keep subtasks for child directories.
        //Get all files/folders inside the current directory.
        //If the directory is empty or can't be read, exit early.
        List<WordCountTask> subtasks = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files == null) return;
        //Loop through each file/folder in the directory.
        for (File file : files) {
            if (file.isDirectory()) {
                //Create a new WordCountTask for it.
                //fork() it — schedules it to run asynchronously in another thread.
                //Keep track of it in the subtasks list so we can wait for it later.
                WordCountTask task = new WordCountTask(file, word, totalCount);
                task.fork();
                subtasks.add(task);
            } else if (file.getName().endsWith(".txt")) {
                //Count how many times the target word appears in this file.
                //Add it to the shared AtomicInteger result (totalCount).
                totalCount.addAndGet(countWordInFile(file));
            }
        }
        //After forking subdirectory tasks, you wait (join) on all of them to ensure they’re done before this task completes.
        for (WordCountTask task : subtasks) {
            task.join();
        }
    }

    private int countWordInFile(File file) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Convert line to lowercase for case-insensitive match
                line = line.toLowerCase();
                String target = word.toLowerCase();

                // Use regex to match exact words ignoring punctuation
                Matcher matcher = Pattern.compile("\\b" + Pattern.quote(target) + "\\b").matcher(line);
                while (matcher.find()) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

}
