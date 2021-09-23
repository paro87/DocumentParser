package com.paro.documentparser.functions.TXT;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Log4j2
@Component
public class TXTNumberOfWordsCounter implements TXTFunction {
    /**
     * This method finds number of words in the file, path to which is provided
     * @param path path to the file
     */
    @Override
    public void processFile(Path path) {
        log.info("TXTNumberOfWordsCounter: processFile - Processing path: {}", path);
        String line;
        int wordCount = 0;
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            while ((line = bufferedReader.readLine())!=null){
                String[] words = line.split("\\s+");
                wordCount += words.length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("{} - Number of words in the file: {}", path.getFileName().toString(),wordCount);
        System.out.println(path.getFileName().toString()+": word count: "+ wordCount);
    }
}
