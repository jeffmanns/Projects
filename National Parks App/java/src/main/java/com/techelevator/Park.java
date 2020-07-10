package com.techelevator;

import java.time.LocalDate;

public class Park {

	private Long parkId;
	private String name;
	private String location;
	private LocalDate establishDate;
	private Long area;
	private Long visitors;
	private String description;

	public Long getParkId() {
		return parkId;
	}

	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getEstablishDate() {
		return establishDate;
	}

	public void setEstablishDate(LocalDate establishDate) {
		this.establishDate = establishDate;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public Long getVisitors() {
		return visitors;
	}

	public void setVisitors(Long visitors) {
		this.visitors = visitors;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String descriptionMultiLineFormatting() {
		StringBuilder description = new StringBuilder(getDescription());
		String newDescription = null;
		
		int i = 0;
		while ((i = description.indexOf(" ", i + 80)) != -1) {
			description.replace(i, i + 1, "\n");
		}
		newDescription = description.toString();
		return newDescription;
	}

	@Override
	public String toString() {
		return "\n" + name + " National Park" + "\n" + "Location: " + location + "\n" + "Established: " + establishDate
				+ "\n" + "Area: " + String.format("%,d", area) + " sq km" + "\n" + "Annual Visitors: " + String.format("%,d", visitors)
				+ "\n\n" + descriptionMultiLineFormatting();
	}

}
