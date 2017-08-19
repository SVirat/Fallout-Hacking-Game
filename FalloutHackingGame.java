/**
 * This is a recreation of the Fallout video game series' hacking minigame.
 * All ideas of this game go to Bethesda game studios. This is simply my attempt
 * to reprogram the minigame to add more words and more difficulty levels.
 * 
 * The list of words used in this game comes from Google's "enable1.txt" file
 * which has been downloaded into my local computer as "ListOfWords.txt". This
 * list can be found here:
 * https://code.google.com/archive/p/dotnetperls-controls/downloads
 * 
 * @author Virat Singh, svirat@gmail.com
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FalloutHackingGame {

	//variable to store the user's choice of difficulty
	private static int difficulty;

	//the list of words that will be shown during the game, read from
	//local file "ListOfWords.txt"
	private static String[] words = getAllWords();

	//variable that decides the length of the words to be shown
	private static int wordLength;

	//the minimum number of options which will show up
	private static final int MIN_OPTIONS = 4;

	//the list of words in the gameplay, number of which are selected randomly,
	//minimum of which is one more than the number of attempts
	private static String[] selectedWords;

	//the correct answer which is randomly selected every time
	private static String answer;

	//number of attempts the player has
	private static final int NUMB_ATTEMPTS = MIN_OPTIONS - 1;

	public static void main(String[] args) {

		//variables to determine if the player wants to play or read rules
		boolean repeat = true;
		int choice = 0;

		while(repeat) {
			System.out.println("Welcome to Fallout Hacking Game!");
			System.out.println("1. Play");
			System.out.println("2. Rules");
			System.out.println("3. About");
			try {
				choice = Integer.parseInt(new Scanner(System.in).next());
				if(choice <= 3 && choice >= 1) {
					repeat = false;
				}
			}catch(Exception e) {
				choice = 0;
				repeat = true;
			}

			//Play the game
			if(choice == 1) {
				String choice2;
				boolean repeat2 = true;
				while(repeat2) {
					//getting the user to select a difficulty level
					difficulty = getDifficulty();
					//based on difficulty, word length is selected
					wordLength = getWordLength(difficulty);
					//based on word length, choose specific words to be shown
					selectedWords = new 
							String[(int)(Math.random() * wordLength) + MIN_OPTIONS];
					//chooses which words to select based on difficulty
					getWordSelection();
					//randomly selects an answer from the chosen words
					answer = selectedWords[(int)
					                       (Math.random()*
					                    		   (selectedWords.length))];
					//starts the game!
					playFalloutHackingGame();
					System.out.println("Do you want to play again?");
					System.out.println("Press only enter for yes, "
							+ "any button for no.");
					//let user decide if they want to play again
					try {
						choice2 = new Scanner(System.in).nextLine();
						if(choice2.isEmpty()) {
							repeat2 = true;
						}
						else {
							repeat2 = false;
						}
					}catch(Exception e) {
						repeat2 = false;
					}
				}
			}

			//Explain rules to player
			else if(choice == 2) {
				System.out.println("Choose a word, and the number "
						+ "of alphabets in"
						+ " the same position in the correct word will be "
						+ "displayed.");
				System.out.println("Using this information, try guessing "
						+ "which word is the secret word!");
				System.out.println("You have "
						+ NUMB_ATTEMPTS + " attempts to win!");
				System.out.println();
				repeat = true;
			}

			//Give player information about the game
			else if(choice == 3){
				System.out.println("This is a recreation of the Fallout video"
						+ " game series' hacking minigame.");
				System.out.println("All ideas of this game go to Bethesda " +
						"Game Studios.");
				System.out.println("This is simply my attempt to reprogram the " +
						"minigame to add more words and more difficulty"
						+ " options.");
				System.out.println("The list of words used in this game come " +
						"from Google's \"enable1.txt\" file.");
				System.out.println("The file can be found at: ");
				System.out.println("https://code.google.com/archive/p/"
						+ "dotnetperls-controls/downloads");
				System.out.println(" - Virat Singh, svirat@gmail.com");
				System.out.println();
				repeat = true;
			}
		}
	}

	/**
	 * The play method starts the game, and lets the user input their guesses.
	 * It also compares the guess to the correct word and is responsible for a
	 * game over if the user runs out of all their attempts.
	 */
	private static void playFalloutHackingGame(){
		//let the user guess the word a certain number of times
		for(int i = 0; i < NUMB_ATTEMPTS; i++){
			String input = "";
			boolean validWord = false;
			//loop back if user enters an invalid option
			while(!validWord){
				System.out.println();
				System.out.print("Guess (" + (NUMB_ATTEMPTS - i) + " left): ");
				//receives and converts user input 
				//to upper case to compare easily
				input = new Scanner(System.in).next().toUpperCase();
				//exit the loop if the input is a valid word
				if(hasWord(selectedWords, input)){ 
					validWord = true;
				}
				else {
					System.out.println("Try again. That word is not valid.");
				}
			}
			//return if user enters the correct answer
			if(input.equals(answer)){
				System.out.println("You win! " + wordLength + "/" +
						wordLength + " guessed!");
				return;
			} 
			//otherwise, remove an attempt and let user try again and tell
			// number of correct letters between input and answer
			else{
				int numLettersRight = 0;
				for(int j = 0; j < answer.length(); j++){
					if(input.charAt(j) == answer.charAt(j)) {
						numLettersRight++;
					}
				}
				System.out.println(numLettersRight + 
						"/" + wordLength + " correct");
			}
			//if all attempts are over, end game
			if(i == NUMB_ATTEMPTS - 1) {
				System.out.println("Game over! "
						+ "The correct word was " + answer + ".");
			}
		}
	}

	/**
	 * Helper method that checks if the word the user inputted is a valid word
	 * in the list of words chosen to appear in the game. It also verifies that
	 * no duplicate words are put into the selected word list.
	 * 
	 * @param list the list of words in the current iteration of the game
	 * @param word the word the user has inputted
	 * @return whether the input is in the list of words
	 */
	private static boolean hasWord(String[] list, String word){
		for(int i = 0; i < list.length; i++){
			//return false if error in list of selected words
			if(list[i] == null) {
				return false;
			}
			//return true if input was a valid word
			if(list[i].equals(word)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Chooses which words to print out of the entire list of words, based on
	 * the difficulty chosen by the user. The length of the chosen words is 
	 * determined by the difficulty.
	 */
	private static void getWordSelection(){
		//select as many words of specified length as needed
		for(int i = 0; i < selectedWords.length; i++){
			//start from a random location in the entire list of legal words
			for(int j = (int)(Math.random() * words.length); 
					j < words.length; j++){
				//if the random word matches the required word length, and
				//is not already in the selected list, print it
				if(words[j].length() == wordLength
						&& !hasWord(selectedWords, words[j])){
					selectedWords[i] = words[j].toUpperCase();
					System.out.println(selectedWords[i]);
					//break out so you can select another random word
					break;
				}
			}
		}
	}

	/**
	 * The difficulty selector. It allows the user to choose the difficulty of 
	 * the game between two levels. The difficulty changes the length of the
	 * selected words to appear in the game
	 * 
	 * @return a difficulty level representing game difficulty
	 */
	private static int getDifficulty(){
		boolean validInput = false;
		final int MIN_LEVEL = 1;
		final int MAX_LEVEL = 11;
		int difficulty = 0;
		while(!validInput){
			try{
				System.out.print("Select a difficulty between " + MIN_LEVEL +
						" and " + MAX_LEVEL + ": ");
				difficulty = Integer.parseInt(new Scanner(System.in).next());
				System.out.println();
				if(difficulty <= MAX_LEVEL && difficulty >= MIN_LEVEL) {
					validInput = true;
				}
			} catch(Exception e){
				validInput = false;
			}
		}
		return difficulty;
	}


	/**
	 * Reads the complete list of words from "enable1.txt" and stores it into 
	 * an array list. The file is stored locally as "ListOfWords.txt". The file
	 * can be found here:
	 * https://code.google.com/archive/p/dotnetperls-controls/downloads
	 * 
	 * @return list of words in "enable1.txt"
	 */
	private static String[] getAllWords(){
		final String currDir = System.getProperty("user.dir");
		File file = new File(currDir + "\\enable1.txt");
		ArrayList<String> list = new ArrayList<String>();
		try{
			//open Buffer Read so read all the words faster
			BufferedReader fastRead = new BufferedReader(new FileReader(file));
			String line = fastRead.readLine();
			while(line != null){
				list.add(line);
				line = fastRead.readLine();
			}
			fastRead.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * Chooses the length of the words based on the difficulty level selected
	 * by the player.
	 * 
	 * @param difficulty the level of difficulty selected by the player
	 * @return length of words to be printed out
	 */
	private static int getWordLength(int difficulty) {

		//a standard number chosen to minimize length of word
		final int MIN_DIFFICULTY_LENGTH = 4;
		//a standard number chosen to leap through word size
		final double BUFFER_CONSTANT = 1.5;

		//return the minimum-size length words if easiest difficulty chosen
		if(difficulty == 1) {
			return MIN_DIFFICULTY_LENGTH;
		}

		//otherwise, returns a formula that changes word 
		//length depending on difficulty
		return (int)(MIN_DIFFICULTY_LENGTH + difficulty * BUFFER_CONSTANT
				+ (int)(Math.random() * MIN_DIFFICULTY_LENGTH));
	}

}