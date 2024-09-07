package com.nokia.vocabularytool;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * This class implements personal vocabulary tool 
 * with operations as 
 *  add, remove,  search and list  words along with their meanings.
 * 
 * @author shruti
 * @version 1.0
 */
public class MyPersonalVocabulary {
	private static final Logger logger = LogManager.getLogger(MyPersonalVocabulary.class);

    private static  String VOCABULARY_FILE = "src/main/resources/MyPersonalVocabulary.txt";
    private static Map<String, String> myVocabulary = new TreeMap<>();

    public static void setVocabularyFile(String filePath) {
        VOCABULARY_FILE = filePath;
    }
    public static Map<String, String> getMyVocabulary() {
        return myVocabulary;
    }
    
    public static void clearMyVocabulary() {
        myVocabulary.clear();
    }

    public static void main(String[] args) {
        //Load file at startup
    	loadVocabulary();

        //For unexpected closure
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
             logger.info("Saving any unsaved data before  shutting down...");
            save();
        }));

        Scanner scanner = new Scanner(System.in);
        
        try
        {

        
        while (true) {
        	  
            System.out.println("\nSelect an operation: ");
            System.out.println("l: List words");
            System.out.println("a: Add a word");
            System.out.println("s: Search a word");
            System.out.println("r: Remove a word");
            System.out.println("q: Quit and save");
            String operation = scanner.nextLine().trim().toLowerCase();
            
            switch (operation) {
                case "l":
                	list();
                    break;
                case "a":
                    add(scanner);
                    break;
                case "s":
                	search(scanner);
                    break;
                case "r":
                	remove(scanner);
                    break;
                case "q":
                    save();
                     logger.info("Vocabulary saved to file store. Exiting application...");
                    return;
                default:
                    logger.info("Not a valid operation, Please select again.");
              }
        }
    }
    catch (Exception e) {
    	logger.error("Something bad happened, system will shut. Error Detail -  "+ e.getMessage());
     } finally {
         if (scanner != null) {
            scanner.close();
        }
    }
}   

   

    public static void list() {
        if (myVocabulary.isEmpty()) {
         	logger.error("Vocabulary is empty.");

        } else {            
            for (Map.Entry<String, String> entry : myVocabulary.entrySet())
            {
               System.out.println(entry.getKey() + ": " + entry.getValue());
             }
        }
    }

    public static void add(Scanner scanner) {
        System.out.print("Please enter the word: ");
        String word = scanner.nextLine().trim().toLowerCase();
        System.out.print("Please enter the meaning: ");
        String meaning = scanner.nextLine().trim();
		if (isValidString(word)) {
			if (myVocabulary.containsKey(word)) {
				System.out.print(
						"Entered word already present in vocabulary. Do you want to change it's the meaning? (y/n): ");
				if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
					myVocabulary.put(word, meaning);
					System.out.println("Changed the meaning.");
				} else {
					System.out.println("Nothing changed");
				}
			} else {
				myVocabulary.put(word, meaning);
				System.out.println("Added the word and meaning you just entered.");
			}
		}
		else {
			System.out.println("Invalid input");
 		}
    }

    public static void remove(Scanner scanner) {
        System.out.print("Please Enter a word to remove: ");
        String word = scanner.nextLine().trim().toLowerCase();

        if (myVocabulary.containsKey(word) && myVocabulary.remove(word) != null) {
            System.out.println("Entered word is removed.");
        } else {
            System.out.println("Vocabulary does not contain this word");
        }
    }

    public static void save() {
        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(VOCABULARY_FILE))) {
            for (Map.Entry<String, String> entry : myVocabulary.entrySet()) {
                bufferWriter.write(entry.getKey() + ":" + entry.getValue());
                bufferWriter.newLine();
            }
        } catch (IOException e) {
        	logger.error("Something went wrong while saviing to file." +e.getMessage());
         }
    }

    public static void search(Scanner scanner) {
        System.out.println("Please enter a word to search in vocabulary file: ");
        String key = scanner.nextLine().trim().toLowerCase();
        String value = myVocabulary.get(key);

        if (value != null) {
            System.out.println(key + ": " + value);
        } else {
            System.out.println("Vocabulary does not contain this word.");
        }
    }
   

    public static void loadVocabulary() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(VOCABULARY_FILE))) {
            String keyValue;
            while ((keyValue = fileReader.readLine()) != null) {
                String[] parts = keyValue.split(":", 2);
                if (parts.length == 2) {
                    myVocabulary.put(parts[0].trim(), parts[1].trim());
                }
            }
            list();
        } 
        
        
        catch (FileNotFoundException e) {
        	logger.error("File not found, loading empty vocabulary." +e.getMessage());

         } catch (IOException e) {
         	logger.error("Something went wrong while loading file." +e.getMessage());
         }
    }

    public static boolean isValidString(String str) {
        // Check if the string is empty or null
        if (str == null || str.isEmpty()) {
            System.out.println("String is empty or null.");
            return false;
        }
        
        // Check if the string contains only alphabetic characters
        if (!str.matches("[a-zA-Z]+")) {
            System.out.println("String contains invalid characters. Only alphabetic characters are allowed.");
            return false;
        }
      return true;  
    }
}
