package org.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringifyFileContentsTest {

    @Test
    @Tag("unit")
    void testValidFile() {
        Path fp = Paths.get("src/test/resources");
        String stringFromFile = StringifyFileContents.toString("a-text-file.txt", fp);
        assertEquals(stringFromFile, "The quick brown fox jumps over the lazy dog");
    }

    @Test
    @Tag("unit")
    void testInvalidPath() {
        Path fp = Paths.get("src/test/reso");
        String stringFromFile = StringifyFileContents.toString("a-text-file.txt", fp);
        assertEquals(stringFromFile, "\nAn error reading file occured :c");
    }

    @Test
    @Tag("unit")
    void testInvalidFileName() {
        Path fp = Paths.get("src/test/resources");
        String stringFromFile = StringifyFileContents.toString("non-existent-text-file.txt", fp);
        assertEquals(stringFromFile, "\nAn error reading file occured :c");
    }
}
