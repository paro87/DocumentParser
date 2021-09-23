package com.paro.documentparser.functions.TXT;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Log4j2
@Component
public class TXTNumberOfDotsCounter implements TXTFunction {
    /**
     * This method finds the number of dots in the file, path to which is provided
     * @param path path to the file
     */
    @Override
    public void processFile(Path path) {
        log.info("TXTNumberOfDotsCounter: processFile - Processing path: {}", path);
        String line;
        int dotCount = 0;
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            while ((line = bufferedReader.readLine())!=null){
                long count = line.chars().filter(ch -> ch == '.').count();
                dotCount += count;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("{} - Number of dots in the file: {}", path.getFileName().toString(),dotCount);
        System.out.println(path.getFileName().toString()+": dot count: "+ dotCount);
    }
}
