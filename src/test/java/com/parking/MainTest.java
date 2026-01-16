package com.parking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Main Application Tests")
class MainTest {

    @Test
    @DisplayName("Should run main method without errors")
    void testMainMethod() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            Main.main(new String[]{});

            String output = outContent.toString();
            assertTrue(output.contains("Hello world!"), "Should print 'Hello world!'");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Main class should not be null")
    void testMainClassExists() {
        assertNotNull(Main.class, "Main class should exist");
    }
}

