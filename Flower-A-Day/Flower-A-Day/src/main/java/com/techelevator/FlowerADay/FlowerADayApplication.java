package com.techelevator.FlowerADay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class FlowerADayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowerADayApplication.class, args);

		Flower flower = new Flower();
		ObjectMapper objectMapper = new ObjectMapper();
		
		final String API_BASE_URL = "https://pixabay.com/api/?key=17646055-e0e93f137cbae14c739247758&q=";
		RestTemplate restTemplate = new RestTemplate();
		
		String requestedFlower = restTemplate.getForObject(API_BASE_URL + flower.getRandomColor() + "+" + flower.getRandomFlowerType() + "+flowers&image_type=photo", String.class);
		System.out.println(requestedFlower);
		
		try {
			JsonNode jsonNode = objectMapper.readTree(requestedFlower);
			String flowerURL = jsonNode.at("/hits/largeImageURL").asText();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		
	}
	
	

}
