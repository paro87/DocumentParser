package com.paro.documentparser.functions.TXT;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class TXTMostUsedWordsCounter implements TXTFunction {

    /**
     * This method finds the most used word in the file, path to which is provided
     * @param path path to the file
     */
    @Override
    public void processFile(Path path) {
        log.info("TXTMostUsedWordsCounter: processFile - Processing path: {}", path);
        String line;
        Map<String, Integer> freq = new HashMap<>();
        int wordCount = 0;
        String mostUsedWord="";
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            while ((line = bufferedReader.readLine())!=null){
                String[] words = line.toLowerCase().split("([,.!?\\s]+)");
                for (String word : words) {
                    if (freq.containsKey(word)) {
                        freq.computeIfPresent(word, (w, c) -> c + 1);
                    }
                    else {
                        freq.putIfAbsent(word, 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String,Integer> entry : freq.entrySet()) {
            if (entry.getValue() > wordCount) {
                wordCount = entry.getValue();
                mostUsedWord = entry.getKey();
            }
        }
        log.info("{} - Most used word: {}", path.getFileName().toString(),mostUsedWord);
        System.out.println(path.getFileName().toString()+": most used word: "+ mostUsedWord);
    }
}
