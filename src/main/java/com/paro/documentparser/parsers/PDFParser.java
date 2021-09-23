package com.paro.documentparser.parsers;


import com.paro.documentparser.model.FileType;

import java.nio.file.Path;

public class PDFParser implements DocumentParser{

    private final String fileType = FileType.PDF.getDocumentExtension();

    @Override
    public String getParserFileType() {
        return fileType ;
    }



    @Override
    public void parseFile(Path path, String moveToFolder) {

    }

    @Override
    public void moveFile(Path path, String moveToFolder) {

    }


}
