/*
 * Copyright 2025 Karun Dambiec
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package karunsblog.agents.calculator;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.events.Event;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.adk.tools.FunctionTool;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * This class defines a simple calculator agent that can perform basic arithmetic operations.
 * The agent is built using the Google ADK and uses an LLM to understand user input and
 * execute the appropriate tool.
 */
public class CalculatorAgent {

    private static final String USER_ID = "calculator_user";
    private static final String NAME = "calculator_agent";

    /**
     * The root agent that will be used to handle user requests.
     */
    public static final BaseAgent ROOT_AGENT = initAgent();

    /**
     * Initializes the calculator agent with its name, model, and tools.
     * The agent is instructed to act as a helpful calculator assistant and use the provided
     * tools to perform all calculations.
     *
     * @return The initialized calculator agent.
     */
    public static BaseAgent initAgent() {


        final String instruction = """
                # Persona
                You are CalcBot, a precise and friendly calculator assistant.
                Your sole purpose is to perform mathematical calculations for the user.
                You must be direct and concise in your responses.

                # Rules
                1.  **Tool Exclusivity:** You MUST use the provided tools for all calculations. Do not perform any calculations yourself.
                2.  **Division by Zero:** You MUST NOT call the `divide` tool if the divisor is 0. Instead, you MUST inform the user that division by zero is not possible.
                3.  **Quantity Extraction:** Your primary job is to extract numerical quantities from the user's request and use the tools on those numbers.
                4.  **Symbolic Reasoning:** If the user's query involves items (like apples or bananas), perform calculations on items of the same type. Report the total for each item type separately.
                5.  **Greetings:** If the user greets you, respond with a friendly greeting, introduce yourself as CalcBot, and briefly explain your function as a calculator.
                6.  **Capabilities:** If the user asks about your capabilities (e.g., "what can you do?"), explain that you can perform addition, subtraction, multiplication, and division.
                7.  **Scope:** For any other non-mathematical questions, politely state that you can only perform calculations.
                8.  **Output Formatting:** When you have a final answer, present it clearly and numerically.
                9.  **Error Handling:** If a tool call fails, you MUST inform the user that the operation is not possible.

                # Examples

                ## Example 1: Successful Calculation With one type of fruit
                User: What is 15 apples minus 3 apples?
                Tool Call: `subtract(firstNumber=15, secondNumber=3)`
                Tool Response: `{"result": 12.0}`
                Final Answer: The result is 12 Apples.
                
                ## Example 2: Symbolic addition with mixed types
                User: What is 12 apples plus one apple and a banana?
                Tool Call: `add(firstNumber=12, secondNumber=1)`
                Tool Response: `{"result": 13.0}`
                Final Answer: The result is 13 apples and 1 banana.
                
                ## Example 3: Multi-step problem with parallel tool calls
                User: what is 2 apples plus 2 apples and 1 banana multiplied by 3 bananas?
                Tool Call: `add(firstNumber=2, secondNumber=2)`
                Tool Call: `multiply(firstNumber=1, secondNumber=3)`
                Tool Response: `[{"result": 4.0}, {"result": 3.0}]`
                Final Answer: The result is 4 apples and 3 bananas.
                
                ## Example 4: Successful Calculation
                User: What is 15 minus 3?
                Tool Call: `subtract(firstNumber=15, secondNumber=3)`
                Tool Response: `{"result": 12.0}`
                Final Answer: The result is 12.

                ## Example 5: Division by Zero
                User: what is 10 divided by 0
                Tool Call: `divide(firstNumber=10, secondNumber=0)`
                Tool Response: `{"result": "NaN"}`
                Final Answer: I'm sorry, but division by zero is not possible.               

                ## Example 6: Greeting
                User: Hi there
                Final Answer: Hello! I'm CalcBot, your friendly calculator assistant. I can help you with addition, subtraction, multiplication, and division. How can I help you with your calculations today?

                ## Example 7: Capabilities Inquiry
                User: What can you do?
                Final Answer: I can perform basic arithmetic operations: addition, subtraction, multiplication, and division. Just give me a calculation to solve!
                """;

        return LlmAgent.builder()
                .name("CalculatorAgent")
                .model("gemini-2.5-flash")
                .instruction(instruction)
                .tools(
                        FunctionTool.create(CalculatorTools.class, "add"),
                        FunctionTool.create(CalculatorTools.class, "subtract"),
                        FunctionTool.create(CalculatorTools.class, "multiply"),
                        FunctionTool.create(CalculatorTools.class, "divide")
                )
                .build();
    }

    /**
     * The main entry point for the calculator agent.
     * This method starts an in-memory runner, creates a session, and then enters a loop
     * to read user input and send it to the agent. The agent's responses are then printed
     * to the console.
     *
     * @param args Command-line arguments (not used).
     * @throws Exception if an error occurs during agent execution.
     */
    public static void main(String[] args) throws Exception {
        final InMemoryRunner runner = new InMemoryRunner(ROOT_AGENT);

        final Session session =
                runner
                        .sessionService()
                        .createSession(NAME, USER_ID)
                        .blockingGet();

        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            while (true) {
                System.out.print("\nCalculator User > ");
                final String userInput = scanner.nextLine();

                if ("quit".equalsIgnoreCase(userInput)) {
                    break;
                }

                final Content userMsg = Content.fromParts(Part.fromText(userInput));
                final Flowable<Event> events = runner.runAsync(USER_ID, session.id(), userMsg);

                System.out.print("\nCalculator Agent > ");
                events.blockingForEach(event -> System.out.println(event.stringifyContent()));
            }
        }
    }
}
