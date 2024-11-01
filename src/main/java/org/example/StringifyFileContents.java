package org.example;

import static org.example.Colors.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StringifyFileContents {

  // toString method transforms file object into string
  // or sends an exception
  public static String toString(String fileName, Path fp) {
    try {
      Path filePath = fp.resolve(fileName);
      String fileContent = Files.readString(filePath);
      System.out.println(setRed() + "Before: \n" + resetColor() + fileContent);
      return fileContent;
    } catch (IOException e) {
      e.getStackTrace();
      return "\nAn error reading file occured :c";
    }
  }
}
