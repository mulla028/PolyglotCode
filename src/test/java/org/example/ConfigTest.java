package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.*;

public class ConfigTest {

  private String originalUserHome;

  @BeforeEach
  void setUp() {
    // Save the original user.home property
    originalUserHome = System.getProperty("user.home");
  }

  @AfterEach
  void tearDown() {
    // Restore the original user.home property
    System.setProperty("user.home", originalUserHome);
  }

  @Test
  @Tag("unit")
  void testConfigLoadsDefaultsWhenFileIsEmpty() throws IOException {
    // Create a temporary directory to act as the user's home directory
    File tempDir = Files.createTempDirectory("tempHomeDir").toFile();

    // Set the user.home property to the temporary directory
    System.setProperty("user.home", tempDir.getAbsolutePath());

    // Ensure the config file does not exist
    File configFile = new File(tempDir, ".polyglotcode-.polyglotcode-config.toml");
    if (configFile.exists()) {
      configFile.delete();
    }

    // Create an empty config file
    configFile.createNewFile();

    // Create the Config object
    Config config = new Config();

    // Test that defaults are loaded
    assertEquals("", config.getApiKey());
    assertEquals("https://api.cohere.ai/v1/chat", config.getBaseUrl());
    assertFalse(config.displayToken());
    assertFalse(config.streamResponse());

    // Clean up
    configFile.delete();
    tempDir.delete();
  }

  @Test
  @Tag("conf")
  void testConfigLoadsValuesFromFile() throws IOException {
    File file = new File("src/test/resources");

    // Set the user.home property to the temporary directory
    System.setProperty("user.home", file.getAbsolutePath());

    // Create the Config object
    Config config = new Config();

    // Test that values are loaded from the file
    assertEquals("test-api-key", config.getApiKey());
    assertEquals("https://api.cohere.ai/v1/chat", config.getBaseUrl());
    assertFalse(config.displayToken());
    assertFalse(config.streamResponse());
  }

  @Test
  @Tag("unit")
  void testConfigThrowsExceptionWhenFileIsInvalid() throws IOException {
    // Create a temporary directory to act as the user's home directory
    File tempDir = Files.createTempDirectory("tempHomeDir").toFile();

    // Set the user.home property to the temporary directory
    System.setProperty("user.home", tempDir.getAbsolutePath());

    // Create the config file with invalid content
    File configFile = new File(tempDir, ".polyglotcode-config.toml");
    Files.write(configFile.toPath(), "invalid content".getBytes());

    // Expect a RuntimeException when creating the Config object
    Exception exception = assertThrows(RuntimeException.class, Config::new);

    assertEquals("Config file is not valid.", exception.getMessage());

    // Clean up
    configFile.delete();
    tempDir.delete();
  }

  @Test
  @Tag("unit")
  void testConfigCreatesDefaultConfigWhenFileDoesNotExist() throws IOException {
    // Create a temporary directory to act as the user's home directory
    File tempDir = Files.createTempDirectory("tempHomeDir").toFile();

    // Set the user.home property to the temporary directory
    System.setProperty("user.home", tempDir.getAbsolutePath());

    // Ensure the config file does not exist
    File configFile = new File(tempDir, ".polyglotcode-config.toml");
    if (configFile.exists()) {
      configFile.delete();
    }

    // Create the Config object
    Config config = new Config();

    // Test that defaults are loaded
    assertEquals("", config.getApiKey());
    assertEquals("https://api.cohere.ai/v1/chat", config.getBaseUrl());
    assertFalse(config.displayToken());
    assertFalse(config.streamResponse());

    // Verify that the config file is created
    assertTrue(configFile.exists());

    // Clean up
    configFile.delete();
    tempDir.delete();
  }
}
