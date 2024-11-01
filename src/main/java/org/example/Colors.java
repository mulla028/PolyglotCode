package org.example;

public class Colors {
  static String setGreen() {
    return "\u001B[32m";
  }

  static String setRed() {
    return "\u001B[31m";
  }

  static String resetColor() {
    return "\u001B[0m";
  }
}
