package org.example;

import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.List;

@CommandLine.Command(
        name="./polyglot",
        version = "PolyglotCode 1.0",
        description = "A command-line tool that helps to translate code in ANY programming language.",
        customSynopsis = "./polyglot <files>... <language> [OPTIONS]"
)
public class Main implements Callable<Integer> {

    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    @CommandLine.Parameters(arity = "2..*",  description = "Enter file names to translate followed by language name you want to translate in")
    private List<String> args;

    // Flags
    @Option(
            names = {"-h", "--help"},
            usageHelp = true
    )
    private boolean helpRequest;

    @Option(
            names = {"-v", "--version"},
            versionHelp = true
    )
    private boolean versionRequested;

    @Option(
            names = {"-a", "--api-key"},
            defaultValue = "",
            description = "Modifying api key manually"
    )
    private String api;

    @Option(
            names = {"-o", "--output"},
            defaultValue = "",
            description = "Creates output file with chosen name"
    )
    private String outputFile;

    @Option(
            names = {"-u", "--base-url"},
            defaultValue = "https://api.cohere.ai/v1/chat",
            description = "You may specify baseURL of api(not recommended)"
    )
    private String baseURL;


    // Callable function
    @Override
    public Integer call() throws Exception {

        List<String> fileNames = args.subList(0, args.size() - 1);
        String language = args.get(args.size() - 1);

        System.out.println("Files are: " + fileNames);

        Path projectPath = Paths.get("").toAbsolutePath();
        Path folderPath = Paths.get("examples");

        for(int i = 0; i < fileNames.size(); i++) {

            System.out.println("Attempting to translate " + fileNames.get(i) + "...");

            String result = "";
            String content = "";

            Path projectFilePath = projectPath.resolve(fileNames.get(i));
            Path filePath = folderPath.resolve(fileNames.get(i));

            if (Files.exists(filePath)) {
                content = StringifyFileContents.toString(fileNames.get(i), folderPath);
            } else if (Files.exists(projectFilePath)) {
                content = StringifyFileContents.toString(fileNames.get(i), projectPath);
            }else {
                throw new Exception("File doesn't exist ;c ");
            }

            result = CohereApi.callApi(content, language, api, baseURL);
            System.out.println(GREEN + "After: \n" + RESET +  result);

            if (!outputFile.equals("")) {
                Path outputPath = Path.of(projectPath + "//" + outputFile);
                System.out.println("Output path is: " + outputPath);
                Files.writeString(outputPath, result);
            }

        }

        return 1;
    }

    public static void main(String[] args) throws Exception {

        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);

    }
}
