package com.techelevator;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
	
	public List<Site> getAvailableSitesByCampgroundId(Long campgroundId, LocalDate startDate, LocalDate endDate);

	public List<Site> getAvailableSitesByParkId(Long parkId, LocalDate startDate, LocalDate endDate);
}
