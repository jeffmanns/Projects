package com.techelevator;

public class Site {
	
	private Long siteId;
	private Long campgroundId;
	private Long siteNumber;
	private Long maxOccupancy;
	private boolean accessible;
	private int maxRvLength;
	private boolean utilities;
	
	
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public Long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public Long getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(Long siteNumber) {
		this.siteNumber = siteNumber;
	}
	public Long getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(Long maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public String isAccessible() {
		String yes = "Yes";
		String no = "No";
		if (accessible) {
			return yes;
		} else {
			return no;
		}
	}
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	public int getMaxRvLength() {
		return maxRvLength;
	}
	public void setMaxRvLength(int maxRvLength) {
		this.maxRvLength = maxRvLength;
	}
	public String isUtilities() {
		String yes = "Yes";
		String no = "N/A";
		if (utilities) {
			return yes;
		} else {
			return no;
		}
	}
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	
	@Override
	public String toString() {
		return String.format("%-15s %-15s %-15s %-15s %-10s", siteNumber, maxOccupancy, isAccessible(), maxRvLength, utilities);
	} 
	

}
