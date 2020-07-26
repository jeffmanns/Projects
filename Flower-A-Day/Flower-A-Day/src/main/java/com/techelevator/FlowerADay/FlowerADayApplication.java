package com.techelevator.FlowerADay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FlowerADayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowerADayApplication.class, args);

	}
	
	Flower flower = new Flower();
	
	private static final String API_BASE_URL = "https://pixabay.com/api/?key=17646055-e0e93f137cbae14c739247758&q=";
	private static RestTemplate restTemplate = new RestTemplate();
	
	public static Flower[] searchAllFlowers() {
		return restTemplate.getForObject(API_BASE_URL + "flowers&image_type=photo", Flower[].class);
	}
	
	public static Flower[] searchFlowersByColor() {
		return restTemplate.getForObject(API_BASE_URL + flower.getRandomColor() + "+flowers" + "&image_type=photo", Flower[].class);
	}
	
	public static Flower[] searchFlowersByType() {
		return restTemplate.getForObject(API_BASE_URL + flower.getRandomFlowerType() + "+flowers" + "&image_type=photo", Flower[].class);
	}
	
	public static Flower[] searchFlowersByColorAndType() {
		return restTemplate.getForObject(API_BASE_URL + flower.getRandomColor() + "+" + flower.getRandomFlowerType() + "+flowers" + "&image_type=photo", Flower[].class);
	}
	

}
