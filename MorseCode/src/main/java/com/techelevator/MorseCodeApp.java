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
		
		
		
		
	}
}
