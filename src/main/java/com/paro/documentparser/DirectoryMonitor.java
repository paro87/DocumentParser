package com.paro.documentparser;

import com.paro.documentparser.parsers.DocumentParser;
import com.paro.documentparser.sources.Source;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardWatchEventKinds.*;
@Log4j2
@Component
public class DirectoryMonitor {

    private Source directorySource;

    private final Map<String, DocumentParser> documentParserMap;

    public DirectoryMonitor(List<DocumentParser> documentParserList){
        documentParserMap = documentParserList.stream().collect(Collectors.toMap(DocumentParser::getParserFileType, Function.identity()));
    }


    public void monitorDirectory(Source source) {
        directorySource = source;

        processExistingFiles(directorySource);

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(directorySource.getDirectoryPath());
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey key;

            while ((key = watchService.take()) != null) {
                Path newPath = null;
                for (WatchEvent<?> event : key.pollEvents()) {

                    WatchEvent.Kind<?> kind = event.kind();

                    if (OVERFLOW == kind) {
                        continue;
                    } else if (ENTRY_CREATE == kind) {
                        if (!isProcessedFolder_ByFileName((((WatchEvent<Path>) event).context()))){
                            newPath = Paths.get(directorySource.getDirectoryPath()).resolve(((WatchEvent<Path>) event).context());
                            log.info("DirectoryMonitor: monitorDirectory - New path created: {}", newPath);
                        }
                    } else if (ENTRY_MODIFY == kind) {
                        if (!isProcessedFolder_ByFileName((((WatchEvent<Path>) event).context()))){
                            newPath = Paths.get(directorySource.getDirectoryPath()).resolve(((WatchEvent<Path>) event).context());
                            log.info("DirectoryMonitor: monitorDirectory - New path modified: {}", newPath);
                        }
                    } else if (ENTRY_DELETE == kind) {
                        newPath = Paths.get(directorySource.getDirectoryPath()).resolve(((WatchEvent<Path>) event).context());
                        log.info("DirectoryMonitor: monitorDirectory - New path deleted: {}", newPath);
                    }
                    processFile(newPath);
                    key.reset();
                }

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processExistingFiles(Source directorySource){
        List<Path> folderFiles = getFolderFiles(directorySource);
        if (!folderFiles.isEmpty()){
            for (Path path : folderFiles) {
                log.info("DirectoryMonitor: processExistingFiles - Existing file is being processed: {}", path);
                processFile(path);

            }
        }
    }

    public List<Path> getFolderFiles(Source source) {
        String directoryPath = source.getDirectoryPath();
        List<Path> folderFiles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(path -> !isProcessedFolder_ByFileName(path.getParent().getFileName()))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.unmodifiableList(folderFiles);
    }


    private boolean isProcessedFolder_ByFileName(Path context) {
        return context.toString().equals(directorySource.getProcessedFolder());
    }

    private void processFile(Path path){
        Path filePath = Optional.ofNullable(path).orElseThrow(NullPointerException::new);
        DocumentParser documentParser = documentParserMap.get(getExtension(filePath));
        if (documentParser==null) {
            log.error("DirectoryMonitor: processFile - File not supported: {}", getExtension(filePath));
        }
        else {
            log.error("DirectoryMonitor: processFile - File is being processed by parser: {} {}",path, documentParser.getParserFileType());
            documentParser.parseFile(filePath, directorySource.getProcessedFolder());
        }
    }

    public String getExtension(Path path) {
        String fileExtension = "";
        String fileName = path.toFile().getName();
        int i = fileName.lastIndexOf('.');
        if (i > 0 &&  i < fileName.length() - 1) {
            fileExtension = fileName.substring(i+1).toLowerCase();
        }
        return fileExtension;
    }


}
