package com.techelevator.FlowerADay;

public class Flower {
	
	private String color;
	private String flowerType;
	
	//CONSTRUCTORS
	
	public Flower() {
		
	}
	
	public Flower(String color, String flowerType) {
		this.color = color;
		this.flowerType = flowerType;
	}
	
	public Flower(String flowerType) {
		this.flowerType = flowerType;
	}
	
	
	// GETTERS AND SETTERS

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFlowerType() {
		return flowerType;
	}

	public void setFlowerType(String flowerType) {
		this.flowerType = flowerType;
	}
	

}
