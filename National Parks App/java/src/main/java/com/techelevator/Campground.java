package com.techelevator;

import java.time.Month;

public class Campground {

	private Long campgroundId;
	private Long parkId;
	private String name;
	private int openFrom;
	private int openTo;
	private double dailyFee;
	
	
	public Long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
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
	public int getOpenFrom() {
		return openFrom;
	}
	public void setOpenFrom(int openFrom) {
		this.openFrom = openFrom;
	}
	public int getOpenTo() {
		return openTo;
	}
	public void setOpenTo(int openTo) {
		this.openTo = openTo;
	}
	public double getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(double dailyFee) {
		this.dailyFee = dailyFee;
	}
	@Override
	public String toString() {
		return String.format("%-40s %-15s %-15s %-10s", name, Month.of(openFrom), Month.of(openTo), String.format("$%.2f", dailyFee));
	}
}