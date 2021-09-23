package com.paro.documentparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DocumentParserApplication {

    private static SourceManager sourceManager;

    public DocumentParserApplication(SourceManager sourceManager) {
        this.sourceManager = sourceManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(DocumentParserApplication.class, args);

        String directoryPath ="";
        if (args.length!=0){
            directoryPath = args[0];
        } else{
            Scanner myObj = new Scanner(System.in);
            System.out.println("Enter absolute directoryPath of a directory to be monitored: ");
            directoryPath = myObj.nextLine();
        }

        sourceManager.manageSource(directoryPath);

    }

}
