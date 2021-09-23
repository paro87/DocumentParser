package com.paro.documentparser.sources;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Log4j2
@Component
public class LocalSource implements Source {
    private String directoryPath;
    private final String processedFolder = "Processed";
    private final String sourceType = "localSource";

    /**
     * This method sets the path to the directory that has to be monitored
     * @param directoryPath the path to the directory that has to be monitored
     * @return
     */
    @Override
    public String setDirectoryPath(String directoryPath) {
        return this.directoryPath=directoryPath;
    }

    /**
     * This method provides the path to the directory that has to be monitored
     * @return
     */
    @Override
    public String getDirectoryPath() {
        return directoryPath;
    }

    /**
     * This method provides the name of the folder, where processed files has to be moved in
     * @return String name of the folder, where processed files has to be moved in
     */
    @Override
    public String getProcessedFolder(){
        return processedFolder;
    }

    /**
     * This method provides the source type
     * @return String the source type
     */
    @Override
    public String getSourceType() {
        return sourceType;
    }

    public boolean isLocalSourceDirectory(String directoryPath){
        Path file = new File(directoryPath).toPath();
        boolean isDirectory = Files.isDirectory(file);
        if (isDirectory){
            return true;
        } else {
            return false;
        }
    }


}
