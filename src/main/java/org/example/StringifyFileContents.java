package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class StringifyFileContents {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    // toString method transforms file object into string
    // or sends an exception
    public static String toString(String fileName, Path fp) {
        try {
            Path filePath = fp.resolve(fileName);
            String fileContent = Files.readString(filePath);
            System.out.println(RED + "Before: \n" + RESET + fileContent);
            return fileContent;
        } catch (IOException e) {
            e.getStackTrace();
            return "\nAn error reading file occured :c";
        }
    }
}
