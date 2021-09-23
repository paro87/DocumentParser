/**
 * This class manages the source type regarding the provided directory path, that is to be monitored
 *
 * @author Palvan Rozyyev
 */
package com.paro.documentparser;

import com.paro.documentparser.sources.Source;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Component
public class SourceManager {

    private final DirectoryMonitor directoryMonitor;
    private final Map<String, Source> sourceMap;

    public SourceManager(DirectoryMonitor directoryMonitor, List<Source> sourceList) {
        this.directoryMonitor = directoryMonitor;
        sourceMap = sourceList.stream().collect(Collectors.toMap(Source::getSourceType, Function.identity()));
    }

    /**
     * This method selects appropriate source type and creates source instance, that will be
     * provided as an argument for the monitoring the provided path.
     * @param directoryPath  path to be monitored
     */
    public void manageSource(String directoryPath){
        if (isDirectory(directoryPath)){
            Source source = sourceMap.get("localSource");
            source.setDirectoryPath(directoryPath);
            directoryMonitor.monitorDirectory(source);
        }
    }

    /**
     * This method checks whether the provided path is directory.
     * @param directoryPath   path to be checked
     * @return boolean        result of the check
     */
    public boolean isDirectory(String directoryPath){
        Path file = new File(directoryPath).toPath();
        boolean isDirectory = Files.isDirectory(file);
        if (isDirectory){
            return true;
        } else {
            return false;
        }
    }
}
