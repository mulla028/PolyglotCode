package org.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;


public class Main {
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.out.println("Usage: polyglot <filename> <language>");
            System.exit(1);
        }

        Path folderPath = Paths.get("example");

        String result = "";
        String content = "";

        String fileName = args[0];
        String language = args[1];

        Path filePath = folderPath.resolve(fileName);

        if (Files.exists(filePath)) {
            content = StringifyFileContents.toString(fileName, folderPath);
//            System.out.println(content);
        } else {
            System.out.println("File doesn't exist");
        }

        result = CohereApi.callApi(content, language);
        System.out.println(GREEN + "After: \n" + RESET +  result);

    }
}
