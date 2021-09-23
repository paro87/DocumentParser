package com.paro.documentparser.functions;

import java.nio.file.Path;

public interface ParserFunction {
    void processFile(Path path);
}
