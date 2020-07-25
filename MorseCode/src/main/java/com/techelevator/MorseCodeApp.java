package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MorseCodeApp {

	public static void main(String[] args) throws FileNotFoundException {

		List<MorseCode> morseCodeList = new ArrayList<>();

		// READ INPUT FROM MORSE CODE TEXT FILE
		File inputTextFile = new File("MorseCodeValues.txt");

		try (Scanner fileScanner = new Scanner(inputTextFile)) {

			while (fileScanner.hasNextLine()) {
				String[] lineInput = fileScanner.nextLine().split(",");
				MorseCode temp = new MorseCode(lineInput[0], lineInput[1]);
				morseCodeList.add(temp);
			}
		}

//		for(MorseCode m : morseCodeList) {
//			System.out.println(m.getCharacters() + " " + m.getDotsAndDashes());
//		}

		// READ INPUT FROM USER

		System.out.println("-----------------------------------------------------------------------");
		System.out.println("Welcome to Morse Code Converter");
		System.out.println("-----------------------------------------------------------------------\n");

		Scanner morseCodeInput = new Scanner(System.in);
		String userInput = "";

		do {
			System.out.println("Please enter something that you would like converted to Morse Code >>> ");

			userInput = morseCodeInput.nextLine();

			// MorseCodeApp app = new MorseCodeApp(); // created an object so I can call the
			// converter method

			System.out.println(userInput + " in Morse Code is: " + converter(userInput, morseCodeList));
			System.out.println("Would you like to run again? (Y/N) ");
			userInput = morseCodeInput.nextLine();
		} while (userInput.equalsIgnoreCase("y"));
	}

	public static String converter(String userInput, List<MorseCode> morseCodeList) {

		String convertedMorseCode = "";

		for (int i = 0; i < userInput.length(); i++) {

			for (MorseCode temp : morseCodeList) {

				if (temp.getCharacters().equals(String.valueOf(userInput.charAt(i)).toUpperCase())) {
					convertedMorseCode += temp.getDotsAndDashes() + " ";
					break;
				}
			}
		}

		return convertedMorseCode;
	}

}
