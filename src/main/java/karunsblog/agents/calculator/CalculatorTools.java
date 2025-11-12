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

import com.google.adk.tools.Annotations.Schema;

import java.util.Map;

/**
 * This class provides a set of basic arithmetic operations that can be used by an agent.
 * Each method is designed to be used as a tool by an LLM agent, with clear descriptions
 * for the model to understand its capabilities.
 */
public class CalculatorTools {

    /**
     * Adds two numbers and returns the result.
     *
     * @param a The first number.
     * @param b The second number.
     * @return A map containing the result of the addition.
     */
    @Schema(description = "Adds two numbers.")
    public static Map<String, Double> add(
            @Schema(name = "firstNumber", description = "The first number.") double a,
            @Schema(name = "secondNumber", description = "The second number.") double b
    ) {
        return Map.of("result", a + b);
    }

    /**
     * Subtracts the second number from the first and returns the result.
     *
     * @param a The first number.
     * @param b The second number to subtract.
     * @return A map containing the result of the subtraction.
     */
    @Schema(description = "Subtracts the second number from the first.")
    public static Map<String, Double> subtract(
            @Schema(name = "firstNumber", description = "The first number.") double a,
            @Schema(name = "secondNumber", description = "The second number to subtract.") double b
    ) {
        return Map.of("result", a - b);
    }

    /**
     * Multiplies two numbers and returns the result.
     *
     * @param a The first number.
     * @param b The second number.
     * @return A map containing the result of the multiplication.
     */
    @Schema(description = "Multiplies two numbers.")
    public static Map<String, Double> multiply(
            @Schema(name = "firstNumber", description = "The first number.") double a,
            @Schema(name = "secondNumber", description = "The second number.") double b
    ) {
        return Map.of("result", a * b);
    }

    /**
     * Divides the first number by the second and returns the result.
     *
     * @param a The dividend (the number being divided).
     * @param b The divisor (the number to divide by).
     * @return A map containing the result of the division.
     * @throws IllegalArgumentException if the divisor is zero.
     */
    @Schema(description = "Divides the first number by the second.")
    public static Map<String, Double> divide(
            @Schema(name = "firstNumber", description = "The dividend (the number being divided).") double a,
            @Schema(name = "secondNumber", description = "The divisor (the number to divide by). This value cannot be 0.") double b
    ) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }
        return Map.of("result", a / b);
    }
}
