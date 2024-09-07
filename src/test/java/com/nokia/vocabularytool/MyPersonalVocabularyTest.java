package com.nokia.vocabularytool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test class for MyPersonalVocabulary implementation
 * 
 * @author shruti
 * @version 1.0
 */
class MyPersonalVocabularyTest {

    private static final String TEST_VOCABULARY_FILE = "src/test/resources/MyPersonalVocabularyTest.txt";
    private static final String MAIN_VOCABULARY_FILE = "src/main/resources/MyPersonalVocabulary.txt"; 

    @BeforeEach
    void setUp() throws IOException {
        // set test file
        MyPersonalVocabulary.setVocabularyFile(TEST_VOCABULARY_FILE);

        // start with clean slate
        MyPersonalVocabulary.clearMyVocabulary();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clear test file
        Files.deleteIfExists(Paths.get(TEST_VOCABULARY_FILE));
         // bring it back to main dile
        MyPersonalVocabulary.setVocabularyFile(MAIN_VOCABULARY_FILE);
    }

    @Test
    void testLoadFromFile() throws IOException {
        // initital data
        Files.write(Paths.get(TEST_VOCABULARY_FILE), "friend:someone you know\nSuomi: Finland\n".getBytes());

        // LOad
        MyPersonalVocabulary.loadVocabulary();
        Map<String, String> vocabulary = MyPersonalVocabulary.getMyVocabulary();

        // Validate the loaded data        
        assertEquals("someone you know", vocabulary.get("friend"));
        assertEquals("Finland", vocabulary.get("Suomi"));
    }

    @Test
    void testAdd() {
        Scanner scanner = new Scanner("mobile\nDevice to talk\n");
        MyPersonalVocabulary.add(scanner);

        Map<String, String> vocabulary = MyPersonalVocabulary.getMyVocabulary();
        assertTrue(vocabulary.containsKey("mobile"));
        assertEquals("Device to talk", vocabulary.get("mobile"));
    }

    @Test
    void testRemove() throws IOException {
        Files.write(Paths.get(TEST_VOCABULARY_FILE), "hero:great people\n".getBytes());
        MyPersonalVocabulary.loadVocabulary();

        Scanner scanner = new Scanner("hero\n");
        MyPersonalVocabulary.remove(scanner);

        Map<String, String> vocabulary = MyPersonalVocabulary.getMyVocabulary();
        assertFalse(vocabulary.containsKey("hero"), "great people");
    }

    @Test
    void testSave() throws IOException {
        
        Scanner scanner = new Scanner("tasty\nsomething yummy\n");
        MyPersonalVocabulary.add(scanner);

        // Save 
        MyPersonalVocabulary.save();

        // load verify
        BufferedReader reader = new BufferedReader(new FileReader(TEST_VOCABULARY_FILE));
        String line;
        boolean present = false;
        while ((line = reader.readLine()) != null) {
            if (line.equals("tasty:something yummy")) {
                present = true;
                break;
            }
        }
        reader.close();
        assertTrue(present);
    }
}
