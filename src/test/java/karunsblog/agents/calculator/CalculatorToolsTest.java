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

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorToolsTest {

    @Test
    void testAdd() {
        Map<String, Double> result = CalculatorTools.add(2, 3);
        assertEquals(5.0, result.get("result"));
    }

    @Test
    void testSubtract() {
        Map<String, Double> result = CalculatorTools.subtract(5, 3);
        assertEquals(2.0, result.get("result"));
    }

    @Test
    void testMultiply() {
        Map<String, Double> result = CalculatorTools.multiply(2, 3);
        assertEquals(6.0, result.get("result"));
    }

    @Test
    void testDivide() {
        Map<String, Double> result = CalculatorTools.divide(6, 3);
        assertEquals(2.0, result.get("result"));
    }

    @Test
    void testDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            CalculatorTools.divide(6, 0);
        });
    }
}
