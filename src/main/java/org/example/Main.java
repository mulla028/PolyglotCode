package org.example;

import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.List;

// Declaration of picoCLI, specifying application usage
@CommandLine.Command(
        name="./polyglot",
        version = "PolyglotCode 0.1",
        description = "A command-line tool that helps to translate code in ANY programming language.",
        customSynopsis = "./polyglot <files>... <language> [OPTIONS]"
)
// Callable Main class
public class Main implements Callable<Integer> {

    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    // CL parameters (arguments), at least 2 arguments required
    @CommandLine.Parameters(arity = "2..*",  description = "Enter file name(s) to translate followed by language name you want to translate in")
    private List<String> args;

    // Flag -h && --help to get info about usage of cli tool
    @Option(
            names = {"-h", "--help"},
            usageHelp = true
    )
    private boolean helpRequest;

    // Flags -v && --version to view version of the application
    @Option(
            names = {"-v", "--version"},
            versionHelp = true
    )
    private boolean versionRequested;

    // Flags -a && --api-key to manually modify api kay
    @Option(
            names = {"-a", "--api-key"},
            defaultValue = "",
            description = "Modifying api key manually"
    )
    private String api;

    // Flags -o && --output to write output to the file specified by user
    @Option(
            names = {"-o", "--output"},
            defaultValue = "",
            description = "Creates output file with chosen name"
    )
    private String outputFile;

    // Flags -u && --base-url to manually modify baseURL of LLM
    @Option(
            names = {"-u", "--base-url"},
            defaultValue = "https://api.cohere.ai/v1/chat",
            description = "You may specify baseURL of api(not recommended)"
    )
    private String baseURL;


    // Function to be called in main file, using for picoCLI
    @Override
    public Integer call() throws Exception {

        // Separate arguments, all arguments except last one
        // to be the fileNames, the last one is chosen language
        List<String> fileNames = args.subList(0, args.size() - 1);
        String language = args.get(args.size() - 1);

        System.out.println("Files are: " + fileNames);

        // Declaration of possible paths where files are being searched
        Path projectPath = Paths.get("").toAbsolutePath();
        Path folderPath = Paths.get("examples");

        // For-loop
        // Works until all files are translated
        for(int i = 0; i < fileNames.size(); i++) {

            System.out.println("Attempting to translate " + fileNames.get(i) + "...");

            String result = "";
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
            }else {
                throw new Exception("File doesn't exist ;c ");
            }

            // Invokes callApi function from CohereApi class
            // assigns the output to the result string
            result = CohereApi.callApi(content, language, api, baseURL);
            System.out.println(GREEN + "After: \n" + RESET +  result);

            // If user specified the output file
            // The result writes to the new file
            // Name of the file specified by user
            if (!outputFile.equals("")) {
                Path outputPath = Path.of(projectPath + "//" + outputFile);
                System.out.println("Output path is: " + outputPath);
                Files.writeString(outputPath, result);
            }

        }

        return 1;
    }

    // main function
    public static void main(String[] args) throws Exception {

        // Invokes call method and assigns to exitCode
        // System exits with the code returned to exitCode by call() method
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);

    }
}
