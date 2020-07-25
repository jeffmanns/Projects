package com.techelevator;

public class MorseCode {
	
	//DATA MEMBERS
	
	private String characters;
	private String dotsAndDashes;
	
	//CONSTRUCTORS
	
	public MorseCode(String characters, String dotsAndDashes) {
		this.characters = characters;
		this.dotsAndDashes= dotsAndDashes;
	}
	
	public MorseCode() {
		
	}
	
	//GETTERS AND SETTERS
	
	public String getCharacters() {
		return characters;
	}
	
	public void setCharacters(String characters) {
		this.characters = characters;
	}
	
	public String getDotsAndDashes() {
		return dotsAndDashes;
	}
	
	public void setDotsAndDashes(String dotsAndDashes) {
		this.dotsAndDashes = dotsAndDashes;
	}
	 
	//OTHER METHODS

}
