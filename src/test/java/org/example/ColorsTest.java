package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ColorsTest {

  @Test
  @Tag("unit")
  void testSetGreen() {
    String expected = "\u001B[32m";
    String actual = Colors.setGreen();
    assertEquals(expected, actual, "setGreen() should return the ANSI code for green color");
  }

  @Test
  @Tag("unit")
  void testSetRed() {
    String expected = "\u001B[31m";
    String actual = Colors.setRed();
    assertEquals(expected, actual, "setRed() should return the ANSI code for red color");
  }

  @Test
  @Tag("unit")
  void testResetColor() {
    String expected = "\u001B[0m";
    String actual = Colors.resetColor();
    assertEquals(expected, actual, "resetColor() should return the ANSI code to reset color");
  }
}
