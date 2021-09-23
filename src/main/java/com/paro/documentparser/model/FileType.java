package com.paro.documentparser.model;

public enum FileType {
    TXT("txt"),
    PDF("pdf"),
    DOC("doc");

    private final String documentExtension;
    FileType(String documentExtension) {
        this.documentExtension = documentExtension;
    }

    public String getDocumentExtension(){
        return this.documentExtension;
    }
}
