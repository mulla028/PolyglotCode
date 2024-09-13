![PolyglotCode](https://github.com/user-attachments/assets/5e39ddae-ebbf-474e-b5fc-e118604b2783)
<div align="center">

**A command-line tool that helps to translate code in ANY programming language**

<br>

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=blue)
![Maven](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=black)

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

</div>

# Getting Started

1. Generate and provide Cohere API key into the Api.java
   ```java
   public static String key = "YOUR_API_KEY";
   ```

2. Make bash script executable, so user would be able to use `./polyglot` command to run code
   ```bash
   chmod +x polyglot
   ```

4. Run Maven Package to compile the source code
   ```bash
   mvn package
   ```

# Usage

```bash
./polyglot <filename> <language>
```

Examples:

```bash
./polyglot main.cpp java
./polyglot examples/example.py c++
```

# Different Flags

### Version info

This flag prints the current version of PolyglotCode and the name of the tool, usage:

```bash
./polyglot -v
./polyglot --version
```

### Help

This flag displays all of the details and configurations that OptimizeIt has, usage:

```bash
./polyglot -h
./polyglot --help
```


