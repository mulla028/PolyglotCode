![PolyglotCode](https://github.com/user-attachments/assets/5e39ddae-ebbf-474e-b5fc-e118604b2783)
<div align="center">

**A command-line tool that helps to translate code in ANY programming language**

<br>

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=blue)
![Maven](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=black)

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

</div>

# Getting Started

1. Generate and provide Cohere API key into the defaultValue of -a && --api-key flag inside of the main class
   ```java
   @Option(
            names = {"-a", "--api-key"},
            defaultValue = "YOUR_API-KEY",
            description = "Modifying api key manually"
    )
    private String api;
   ```
   or
   
   Specify call and provide the api-key into the command line, using flags
    ```bash
    ./python <file(s)...> <language> -a <YOUR-API-KEY>
    ```

3. Make bash script executable, so user would be able to use `./polyglot` command to run code
   ```bash
   chmod +x polyglot
   ```

4. Run Maven Package to compile the source code
   ```bash
   mvn package
   ```

# Usage

```bash
./polyglot <file(s)...> <language> [OPTIONS]
```

Examples:

```bash
./polyglot main.cpp java
./polyglot examples/example.py main.cpp c# -o output.cs
```

# Different Flags

### Version info

This flag prints the current version of PolyglotCode and the name of the tool, usage:

```bash
./polyglot -v
./polyglot --version
```

### Help

This flag displays all of the details and configurations that PolyglotCode has, usage:

```bash
./polyglot -h
./polyglot --help
```

### Specify API - KEY

As it was mentioned earlier, user is able to specify Cohere-Api key without adding it to the defaultValue property of String api inside of the main class, usage:

```bash
./polyglot <file(s)...> <language> -a <YOUR-API>
./polyglot <file(s)...> <language> --api-key <YOUR-API>
```

### Specify baseURL

It is not recommended to change the baseUrl of this application, as it may produce an unexpected output; however this option is still available to the user, usage:

```bash
./polyglot <file(s)...> <language> -u <baseURL>
./polyglot <file(s)...> <language> --base-url <baseURL>
```

### Write the result to the file

Creates the file, specified by user and writes the result there. It is highly recommended to use this option with one file at the time. Usage:

```bash
./polyglot <file> <language> -o file.txt
./polyglot <file> <language> --output file.txt
```

