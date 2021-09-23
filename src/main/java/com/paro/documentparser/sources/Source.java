package com.paro.documentparser.sources;

public interface Source {
    String setDirectoryPath(String directoryPath);
    String getDirectoryPath();
    String getProcessedFolder();
    String getSourceType();
}
