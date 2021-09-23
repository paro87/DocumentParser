package com.paro.documentparser.parsers;


import java.nio.file.Path;

public interface DocumentParser {
    String getParserFileType();
    void parseFile(Path path, String moveToFolder);
    void moveFile(Path path, String moveToFolder);
}
