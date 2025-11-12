# Google ADK Calculator Agent

This project demonstrates a simple calculator agent built using the Google Agent Development Kit (ADK). The agent can perform basic arithmetic operations (addition, subtraction, multiplication, and division) by leveraging an LLM to interpret user requests and call the appropriate tools.

## Features

*   **Basic Arithmetic Operations**: Supports addition, subtraction, multiplication, and division.
*   **LLM-Powered**: Uses a Large Language Model (LLM) to understand natural language queries.
*   **Tool-Use**: Demonstrates how to define and use tools with the Google ADK.
*   **Robust Division**: Includes error handling for division by zero.

## Project Structure

*   `src/main/java/karunsblog/agents/calculator/CalculatorAgent.java`: The main agent class, responsible for initializing the LLM agent and handling user interactions.
*   `src/main/java/karunsblog/agents/calculator/CalculatorTools.java`: Contains the arithmetic functions exposed as tools to the LLM agent.
*   `src/test/java/karunsblog/agents/calculator/CalculatorToolsTest.java`: JUnit 5 test cases for the `CalculatorTools` class.
*   `pom.xml`: Maven project file, managing dependencies and build configurations.
*   `ARCHITECTURE.md`: A detailed explanation of the project's architecture.

## Getting Started

### Prerequisites

*   Java Development Kit (JDK) 25 or later.
*   Maven 3.6.0 or later.
*   A Google Cloud Project with access to Gemini models (e.g., `gemini-2.5-flash`). Ensure your environment is authenticated to use Google Cloud services.

### Setup

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/GoogleADK-CalculatorAgent.git
    cd GoogleADK-CalculatorAgent
    ```

2.  **Configure Google Cloud Authentication**:
    Ensure your environment is authenticated to use Google Cloud services. You can typically do this by:
    *   Setting the `GOOGLE_APPLICATION_CREDENTIALS` environment variable to the path of your service account key file.
    *   Using the Google Cloud CLI: `gcloud auth application-default login`

3.  **Build the project**:
    ```bash
    mvn clean install
    ```

## Usage

To run the calculator agent, execute the `main` method in `CalculatorAgent.java`:

```bash
mvn exec:java -Dexec.mainClass="karunsblog.agents.calculator.CalculatorAgent"
```

Once the agent is running, you can interact with it through the console:

```
You > What is 5 plus 3?
Agent > The result is 8.0

You > Multiply 10 by 4
Agent > The result is 40.0

You > Divide 100 by 0
Agent > I cannot do that.

You > quit
```

## Running Tests

To run the unit tests for the `CalculatorTools`, use Maven:

```bash
mvn test
```

## Contributing

Feel free to fork this repository, open issues, or submit pull requests.

## License

This project is licensed under the Apache 2.0 License. See the `LICENSE` file for details.
