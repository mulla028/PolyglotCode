package org.example;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.io.File;
import java.nio.file.Files;
import org.json.JSONObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ApiTest {

  private WireMockServer wireMockServer;

  private File fileName = new File("src/test/resources", "response.json");

  @Test
  @Tag("api")
  public void exampleTest() throws Exception {
    // Setup the WireMock mapping stub for the test
    wireMockServer = new WireMockServer(8080);
    wireMockServer.start();
    String body = Files.readString(fileName.toPath());
    JSONObject jsonObject = new JSONObject(body);

    // Configure WireMock to use the started server
    WireMock.configureFor("127.0.0.1", 8080);

    // Stub an API endpoint for testing
    stubFor(
        post(urlEqualTo("/server-response"))
            .willReturn(ok().withHeader("Content-Type", "application/json").withBody(body)));

    // Setup HTTP POST request (with HTTP Client embedded in Java 11+)
    String bodyURL = "http://127.0.0.1:8080/server-response";
    JSONObject response;
    response = CohereApi.callApi("", "", "", bodyURL, false);

    assertEquals(jsonObject.toString(), response.toString());

    wireMockServer.stop();
  }

  @Test
  @Tag("unit")
  public void testGetMsg() {
    String language = "French";
    String fileContent = "Hello World!";
    String expectedMessage = "{ \"message\": \"Translate this code in French\\Hello World!";
    String actualMessage = CohereApi.getMsg(language, fileContent);
    assertEquals(
        expectedMessage,
        actualMessage,
        "The JSON request message should match the expected format.");
  }

  @Test
  @Tag("unit")
  public void testGetErr400() {
    assertEquals(
        "Bad Request. The request was invalid or cannot be otherwise served.",
        CohereApi.getErr(400));
  }

  @Test
  @Tag("unit")
  public void testGetErr401() {
    assertEquals(
        "Unauthorized. Authentication failed or API key is invalid.", CohereApi.getErr(401));
  }

  @Test
  @Tag("unit")
  public void testGetErr403() {
    assertEquals(
        "Forbidden. The request is understood, but it has been refused.", CohereApi.getErr(403));
  }

  @Test
  @Tag("unit")
  public void testGetErr404() {
    assertEquals("Not Found. The requested resource could not be found.", CohereApi.getErr(404));
  }

  @Test
  @Tag("unit")
  public void testGetErr429() {
    assertEquals("Too Many Requests. Rate limit exceeded.", CohereApi.getErr(429));
  }

  @Test
  @Tag("unit")
  public void testGetErr500() {
    assertEquals("Internal Server Error. An error occurred on the server.", CohereApi.getErr(500));
  }

  @Test
  @Tag("unit")
  public void testGetErr503() {
    assertEquals(
        "Service Unavailable. The service is temporarily unavailable.", CohereApi.getErr(503));
  }

  @Test
  @Tag("unit")
  public void testGetErrUnknown() {
    assertEquals("An unexpected error occurred.", CohereApi.getErr(999));
  }
}
