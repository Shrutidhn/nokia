
## Introduction
1. This is intended for setting up and running a CLI program written in java which creates a personal vocabulary tool for keeping and managing the different words along with their meaning.
2. The program stores the vocabulary records in a file called MyPersonalVocabulary.txt. 
3.At the startup program loads the vocabulary and when we close program it saves changes back to file.
4. As the data would be a simple key value pair with a flat structure, I have preferred text file as format instead of JSON for the sake of simplicity and faster read and write when.
5. There is also a test class and project is configured to run with maven as well if you want to build package, test and run as an end-end project.

Program runs on user input and below are defined inputs 

1. l to list all the stored words with their respective meanings in alphabetical order.
2. a to add a new word along with its meaning. Both the word and its meaning should come as user input.
     If the word already exists, it should ask the user to replace the meaning or keep the existing one.
3. s to search for a particular word and display it along with its meaning.
4. r to remove the given word by user.
5. q to close the program.


## Setup and run
Download the program, place it at a location of your choice. Open command prompt and move to the project folder say (vocabulary-tool)
1. Build and test
   Execute - mvn package or mvn clean package
2. Run test    
   Execute - mvn test 
3. Run program with jar package 
   Execute -  java -jar target/vocabulary-tool-1.0-DEV.jar  


**Note** To change the java version modify the java version tag in POM file.


