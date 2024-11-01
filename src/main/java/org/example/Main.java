package org.example;

import static org.example.Colors.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import org.json.JSONObject;
import picocli.CommandLine;
import picocli.CommandLine.Option;

// Declaration of picoCLI, specifying application usage
@CommandLine.Command(
    name = "./polyglot",
    version = "PolyglotCode 0.1",
    description = "A command-line tool that helps to translate code in ANY programming language.",
    customSynopsis = "./polyglot <files>... <language> [OPTIONS]")
// Callable Main class
public class Main implements Callable<Integer> {

  // CL parameters (arguments), at least 2 arguments required
  @CommandLine.Parameters(
      arity = "2..*",
      description =
          "Enter file name(s) to translate followed by language name you want to translate in")
  private List<String> args;

  // Flag -h && --help to get info about usage of cli tool
  @Option(
      names = {"-h", "--help"},
      usageHelp = true)
  private boolean helpRequest;

  // Flags -v && --version to view version of the application
  @Option(
      names = {"-v", "--version"},
      versionHelp = true)
  private boolean versionRequested;

  // Flags -a && --api-key to manually modify api kay
  @Option(
      names = {"-a", "--api-key"},
      defaultValue = "",
      description = "Modifying api key manually")
  private String api;

  // Flags -o && --output to write output to the file specified by user
  @Option(
      names = {"-o", "--output"},
      defaultValue = "",
      description = "Creates output file with chosen name")
  private String outputFile;

  // Flags -u && --base-url to manually modify baseURL of LLM
  @Option(
      names = {"-u", "--base-url"},
      defaultValue = "",
      description = "You may specify baseURL of api(not recommended)")
  private String baseURL;

  // Flags -t && --token-usage to display token information
  @Option(
      names = {"-t", "--token-usage"},
      description = "Display token information")
  private boolean tokenUsage;

  @Option(
      names = {"-s", "--stream"},
      description = "Stream the response live as it updates.")
  private boolean stream;

  // Function to be called in main file, using for picoCLI
  @Override
  public Integer call() throws Exception {

    Colors clr = null;

    // Read config file
    Config config = new Config();
    if (api.isEmpty()) api = config.getApiKey();
    if (baseURL.isEmpty()) baseURL = config.getBaseUrl();
    if (config.displayToken()) tokenUsage = true;
    if (config.streamResponse()) stream = true;

    // Separate arguments, all arguments except last one
    // to be the fileNames, the last one is chosen language
    List<String> fileNames = args.subList(0, args.size() - 1);
    String language = args.get(args.size() - 1);

    System.out.println("Files are: " + fileNames);

    // Declaration of possible paths where files are being searched
    Path projectPath = Paths.get("").toAbsolutePath();
    Path folderPath = Paths.get("examples");

    // Declare tokens
    int inputTokens = 0;
    int outputTokens = 0;

    // For-loop
    // Works until all files are translated
    for (int i = 0; i < fileNames.size(); i++) {

      System.out.println("Attempting to translate " + fileNames.get(i) + "...");

      JSONObject resultJSON;
      String resultText = "";
      String content = "";

      // Possible file paths to search for
      Path projectFilePath = projectPath.resolve(fileNames.get(i));
      Path filePath = folderPath.resolve(fileNames.get(i));

      // if - else if - else statement
      // if file exists in /examples or root of the project
      // file is getting transformed to the string
      // otherwise it throws an error
      if (Files.exists(filePath)) {
        content = StringifyFileContents.toString(fileNames.get(i), folderPath);
      } else if (Files.exists(projectFilePath)) {
        content = StringifyFileContents.toString(fileNames.get(i), projectPath);
      } else {
        throw new Exception("File '" + fileNames.get(i) + "' does not exist.");
      }

      // Invokes callApi function from CohereApi class
      // assigns the output to the result string
      resultJSON = CohereApi.callApi(content, language, api, baseURL, stream);

      if (!stream) {
        resultText = resultJSON.getString("text");

        // *** Remove code fences if present ***
        resultText = removeCodeFences(resultText);

        System.out.println(setGreen() + "After: \n" + resetColor() + resultText);

        // If user specified the output file, write the result to the file
        if (!outputFile.equals("")) {
          Path outputPath = projectPath.resolve(outputFile);
          System.out.println("Output path is: " + outputPath);
          Files.writeString(outputPath, resultText);
        }

        // Handle token usage
        if (tokenUsage && resultJSON.has("meta")) {
          Tokens tokens = Tokens.fromJson(resultJSON.getJSONObject("meta"));
          inputTokens += tokens.getInputTokens();
          outputTokens += tokens.getOutputTokens();
        }

      } else {
        if (!outputFile.isEmpty()) {
          resultText = resultJSON.getString("text");
          Path outputPath = projectPath.resolve(outputFile);
          System.out.println("Output path is: " + outputPath);
          Files.writeString(outputPath, resultText);
        }
      }

      // Extract token information from resultJSON
    }

    // If -t option is present
    // Output token information to stderr
    if (tokenUsage && !stream) {
      System.err.println(
          "\n------------------------\n"
              + "Token Information:\n"
              + "Input Tokens: "
              + inputTokens
              + "\n"
              + "Output Tokens: "
              + outputTokens
              + "\n"
              + "Total Tokens: "
              + (inputTokens + outputTokens));
    }

    return 0;
  }

  // main function
  public static void main(String[] args) throws Exception {

    // Invokes call method and assigns to exitCode
    // System exits with the code returned to exitCode by call() method
    int exitCode =
        new CommandLine(new Main())
            .setExecutionExceptionHandler(
                new CommandLine.IExecutionExceptionHandler() {
                  @Override
                  public int handleExecutionException(
                      Exception ex, CommandLine cmd, CommandLine.ParseResult parseResult) {
                    System.err.println("Error: " + ex.getMessage());
                    return 1; // Non-zero exit code for errors
                  }
                })
            .execute(args);
    System.exit(exitCode);
  }

  private String removeCodeFences(String text) {
    if (text.startsWith("```")) {
      // Find the position of the first newline after the opening ```
      int firstNewline = text.indexOf('\n');
      if (firstNewline != -1) {
        // Remove the opening code fence and optional language identifier
        text = text.substring(firstNewline + 1);
      } else {
        // If there's no newline, remove the opening code fence
        text = text.substring(3);
      }
    }

    if (text.endsWith("```")) {
      // Remove the closing code fence
      text = text.substring(0, text.length() - 3);
    }

    return text.trim(); // Trim any leading/trailing whitespace
  }
}
