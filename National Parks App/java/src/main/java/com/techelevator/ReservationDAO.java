package com.techelevator;

import java.time.LocalDate;

public interface ReservationDAO {
	
	public void bookReservation(Long siteId, String name, LocalDate startDate, LocalDate endDate, LocalDate createDate);
	
}
