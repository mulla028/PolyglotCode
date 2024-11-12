# Contributing to PolyglotCode

Thank you for considering contributing to **PolyglotCode**! Your support and collaboration are greatly appreciated.

## How to Contribute

1. **Fork the Repository**
   - Click the "Fork" button on the top right of this repository's page to create your own copy of the project.
   - Clone your forked repository to your local machine:
     ```bash
     git clone https://github.com/mulla028/PolyglotCode.git
     ```
   - Navigate into the project directory:
     ```bash
     cd PolyglotCode
     ```

2. **Setting Up Your Environment**
   - Ensure you have Java and Maven installed.
   - Generate and provide your Cohere API key into the `defaultValue` of the `-a` and `--api-key` flags inside the main class:
     ```java
     @Option(
         names = {"-a", "--api-key"},
         defaultValue = "YOUR_API-KEY",
         description = "Modifying API key manually"
     )
     private String api;
     ```
   - Alternatively, you can specify the API key when running the tool (see "Usage").

3. **Build and Test**
   - Make the bash script executable:
     ```bash
     chmod +x polyglot
     ```
   - Compile the source code using Maven:
     ```bash
     mvn package
     ```
   - Run and test the tool to ensure everything works correctly.

4. **Create Your Contribution**
   - Make changes or add new features in your local repository.
   - Please write clear, concise, and well-documented code.
   - Ensure that your code follows the style and conventions of the project.

5. **Commit Your Changes**
   - Create a new branch for your feature or fix:
     ```bash
     git checkout -b feature-name
     ```
   - Commit your changes with a descriptive commit message:
     ```bash
     git add .
     git commit -m "Add a concise description of your change"
     ```

6. **Push to Your Fork**
   - Push your branch to your forked repository:
     ```bash
     git push origin feature-name
     ```

7. **Submit a Pull Request (PR)**
   - Go to the original repository and create a pull request from your forked repository.
   - Provide a clear description of the changes you've made and the problem it solves or feature it adds.
   - Reference any related issues in the pull request description if applicable.

## Guidelines for Contributions

1. **Follow the Coding Standards**
   - Use appropriate naming conventions and indentation.
   - Ensure your code is clean and well-documented.

2. **Document Your Changes**
   - Update the `README.md` file if your changes include new features or configurations.
   - Include examples of how to use new features if applicable.

3. **Testing and Validation**
   - Before submitting your PR, thoroughly test your code.
   - Ensure that your contribution does not break existing functionality.

### Code Style and Linting

We use Checkstyle to ensure that our code adheres to a consistent style. Before submitting your pull request, please run Checkstyle to check for any code style violations.

#### Running Checkstyle

To run Checkstyle on the project, follow these steps:

1. **Ensure Checkstyle is Configured:**
   Make sure the `checkstyle.xml` configuration file is in place. This file defines the rules for code style checks.

2. **Run Checkstyle:**
   Open your terminal, navigate to the project directory, and execute the following command:
   ```bash
   mvn checkstyle:check
   ```

3. **Review Output**
   After running the Checkstyle check, review the output in the terminal for any errors or warnings and resolve them accordingly.

## Code Formatting

We use **Spotless** with Google Java Format to ensure consistent code style. Before submitting your changes, please format your code.

### How to Format Code
- Open your terminal and navigate to the project's root directory.
- Run the following command to format all source code:
  ```bash
  mvn spotless:apply
  ```
## Testing
Upon successful creation of new model or method every developer will be asked to implement unit and integration tests.

### Implement the Tests
- Open `test` folder and navigate yourself to `java` or `integration` directory according to what test you implement.
- `unit` = `java`
- `integration` = `integration`
### How to Run the Tests
Using `JUnit` principals we are able to run all the tests using one command:
```bash
mvn test
```
or using `@Tag` annotation we may separate them and run:
```bash
mvn test -Dgroups=<TagName>
```

## Reporting Issues

1. Use the [GitHub Issues](https://github.com/mulla028/PolyglotCode/issues) section to report bugs or suggest features.
2. When reporting a bug, provide as much detail as possible, including:
   - Steps to reproduce the issue
   - Your environment details (Java version, operating system, etc.)
   - Any relevant error messages or logs

## Additional Notes

- **License**: By contributing, you agree that your contributions will be licensed under the [MIT License](https://opensource.org/licenses/MIT).
- **Community**: Be respectful and considerate of others. We value open communication and constructive feedback.

Happy coding, and thank you for contributing to **PolyglotCode**!
