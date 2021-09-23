
package com.paro.documentparser.parsers;


import com.paro.documentparser.functions.TXT.TXTFunction;
import com.paro.documentparser.model.FileType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Log4j2
@Component
public class TXTParser implements DocumentParser{

    private final FileType fileType = FileType.TXT;

    private final List<TXTFunction> txtFunctionsList;

    @Autowired
    public TXTParser(List<TXTFunction> txtFunctionsList){
        this.txtFunctionsList = txtFunctionsList;
    }

    @Override
    public String getParserFileType() {
        return fileType.getDocumentExtension();
    }

    /**
     * This method performs the parsing operations on the provided file (path) and
     * moves the file to the folder, the name of which provided as an argument
     * @param path path to the file,
     * @param moveToFolder name of the folder, where file has to be moved after the operations
     */
    @Override
    public void parseFile(Path path, String moveToFolder) {
        log.info("TXTParser: parseFile - Parsing path: {}", path);
        for (TXTFunction txtFunction : txtFunctionsList) {
            txtFunction.processFile(path);
        }
        moveFile(path, moveToFolder);
    }

    /**
     * This method performs the moving operations on the provided file (path).
     * @param path path to the file,
     * @param moveToFolder name of the folder, where file has to be moved
     */
    @Override
    public void moveFile(Path path, String moveToFolder) {
        log.info("TXTParser: moveFile - Moving file: {}", path);
        String fileName = path.getFileName().toString();
        Path temp = null;
        try {
            Path newDirectory = Files.createDirectories(path.getParent().resolve(moveToFolder));
            Path newFile = Files.createFile(newDirectory.resolve(fileName));
            temp = Files.move(path, newFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(temp != null){
            log.info("TXTParser: moveFile - File has been moved successfully");
        }else{
            log.error("TXTParser: moveFile - Failed to move the file");
        }
    }


}
