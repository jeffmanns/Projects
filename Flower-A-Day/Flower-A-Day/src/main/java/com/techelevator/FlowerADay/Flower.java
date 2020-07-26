package com.techelevator.FlowerADay;

import java.util.HashMap;
import java.util.Map;

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
	
	// OTHER METHODS
	
	public String getRandomColor() {
		
		Map<Integer, String> mapOfColors = new HashMap<>();
		
		mapOfColors.put(1, "pink");
		mapOfColors.put(2, "peach");
		mapOfColors.put(3, "red");
		mapOfColors.put(4, "purple");
		mapOfColors.put(5, "blue");
		mapOfColors.put(6, "orange");
		mapOfColors.put(7, "yellow");
		mapOfColors.put(8, "white");
		mapOfColors.put(9, "blue");
		
		int min = 1;
		int max = mapOfColors.size();
		int range = max - min + 1;
		
		int randomNum = (int)(Math.random() * range) + 1;		
		
		return mapOfColors.get(randomNum);
	}
	
	public String getRandomFlowerType() {
		
		Map<Integer, String> mapOfFlowerTypes = new HashMap<>();
		
		mapOfFlowerTypes.put(1, "hydrangea");
		mapOfFlowerTypes.put(2, "rose");
		mapOfFlowerTypes.put(3, "tulip");
		mapOfFlowerTypes.put(4, "chrysanthemum");
		mapOfFlowerTypes.put(5, "orchid");
		mapOfFlowerTypes.put(6, "lily");
		mapOfFlowerTypes.put(7, "iris");
		mapOfFlowerTypes.put(8, "hyacinth");
		mapOfFlowerTypes.put(9, "poppy");
		mapOfFlowerTypes.put(10, "wildflower");
		mapOfFlowerTypes.put(11, "wisteria");
		mapOfFlowerTypes.put(12, "bougainvillea");
		mapOfFlowerTypes.put(13, "clematis");
		
		int min = 1;
		int max = mapOfFlowerTypes.size();
		int range = max - min + 1;
		
		int randomNum = (int)(Math.random() * range) + 1;		
		
		return mapOfFlowerTypes.get(randomNum);
	}
	

}
