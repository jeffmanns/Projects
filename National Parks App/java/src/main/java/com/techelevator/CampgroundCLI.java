package com.techelevator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCReservationDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;

public class CampgroundCLI {

	private static final String MAIN_MENU_OPTION_SELECT_PARK = "Select A Park";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_OPTION_SELECT_PARK,
			MAIN_MENU_OPTION_EXIT };

	private static final String CAMPGROUND_MENU_OPTION_SEARCH_FOR_RESERVATION = "Search Parkwide Reservations";
	private static final String CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS_MAKE_RESERVATION = "View Campgrounds and Make Reservation";
	private static final String CAMPGROUND_MENU_OPTION_RETURN_TO_PREVIOUS = "Return to Previous Screen";
	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] {
			CAMPGROUND_MENU_OPTION_SEARCH_FOR_RESERVATION, CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS_MAKE_RESERVATION,
			CAMPGROUND_MENU_OPTION_RETURN_TO_PREVIOUS };

	private Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO;
	private ReservationDAO reservationDAO;
	private Park selectedPark;
	private LocalDate fromDateLocalDate;
	private LocalDate toDateLocalDate;
	private Campground selectedCampground;
	private Long selectedSiteNumber;

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

//CONSTRUCTING PYLONS
	public CampgroundCLI(DataSource dataSource) {
		this.menu = new Menu(System.in, System.out);
		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		siteDAO = new JDBCSiteDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
	}

//START PROGRAM, RUN INITIAL MENU FOR USER -- SELECT PARK HERE
	public void run() {
		while (true) {
			System.out.println("National Park Campsite Reservation System");
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.exit(1);
			} else if (choice.equals(MAIN_MENU_OPTION_SELECT_PARK)) {
				displayParkOptions(); //PUSH TO NEXT MENU/METHOD**
			}
		}
	}

// USER IS SHOWN LIST OF PARKS - USER INPUT ASSOCIATED TO PARK OBJECT
	public void displayParkOptions() {
		while (true) {
			System.out.println("\nSelect a Park for Further Details");
			List<Park> parkOptions = parkDAO.getAllParks();
			String[] parkNames = new String[parkOptions.size() + 1];
			for (int i = 0; i < parkNames.length - 1; i++) {
				parkNames[i] = parkOptions.get(i).getName();
			}
			parkNames[parkNames.length - 1] = "Quit";
			String string = (String) menu.getChoiceFromOptions(parkNames);
			if (string.equals("Quit")) {
				System.out.println("Goodbye");
				System.exit(1);
			}
			for (Park parks : parkOptions) {
				if (string.equalsIgnoreCase(parks.getName())) {
					System.out.println(parks.toString());
					selectedPark = parks;
					displayCampgroundOptions(); //PUSH TO NEXT MENU/METHOD**
				}
			}

		}
	}

//DISPLAY OPTIONS TO USER AFTER SELECTING PARK
	public void displayCampgroundOptions() {

		while (true) {

			System.out.println("\nSelect an option below");
			String choice = (String) menu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS);
			if (choice.equals(CAMPGROUND_MENU_OPTION_VIEW_CAMPGROUNDS_MAKE_RESERVATION)) {
				viewCampgroundAsSelection(); //PUSH TO NEXT MENU/METHOD**
			} else if (choice.equals(CAMPGROUND_MENU_OPTION_SEARCH_FOR_RESERVATION)) {

			} else if (choice.equals(CAMPGROUND_MENU_OPTION_RETURN_TO_PREVIOUS)) {
				break;
			}
			break;
		}
	}

//VIEW CAMPGROUND LIST ASSOCIATING USER CHOICE TO CAMPGROUND OBJECT
	public void viewCampgroundAsSelection() {
		System.out.println("\n***Select a campground by number to search for reservations***");
		List<Campground> campgroundOptions = campgroundDAO.getCampgroundsByParkId(selectedPark.getParkId());
		String[] campgroundToStrings = new String[campgroundOptions.size()];
		for (int i = 0; i < campgroundToStrings.length; i++) {
			campgroundToStrings[i] = campgroundOptions.get(i).toString();
		}
		System.out.printf("\n%-1s %-40s %-15s %-15s %-10s", "  ", "Name", "Open", "Close", "Daily Fee");
		String string = (String) menu.getChoiceFromOptionsHash(campgroundToStrings);
		for (Campground campground : campgroundOptions) {
			if (string.startsWith(campground.getName())) {
				selectedCampground = campground;
			}
		} userFromDateSelection(); //PUSH TO NEXT MENU/METHOD**
	}
	
//TAKE USER INPUT FOR ARRIVAL DATE AND HANDLE ALL CHECKS
	public void userFromDateSelection() {
		while (true) {
			System.out.print("What is the arrival date? (mm/dd/yyyy) >>> ");
			String userFromInput = menu.getUserInput();
			if (dateValidation(userFromInput)) {
				if (checkYearOfDate(userFromInput)) {
					fromDateLocalDate = returnLocalDateFromUserInput(userFromInput);
					break;
				} else {
					System.out.println("\n***INVALID YEAR ENTRY, PLEASE ENTER YEAR BETWEEN 2020 AND 2025****\n");
				}
			} else {
					System.out.println("\n***INVALID MONTH OR DAY, PLEASE TRY AGAIN***\n");
			}
		} userToDateSelection(); //PUSH TO NEXT MENU/METHOD**
	}
	
//TAKE USER INPUT FOR DEPARTURE DATE AND HANDLE ALL CHECKS			
		public void userToDateSelection() {
			while (true) {
				System.out.print("What is the departure date? (mm/dd/yyyy) >>> ");
				String userToInput = menu.getUserInput();
				if (dateValidation(userToInput)) {
					if (checkYearOfDate(userToInput)) {
						toDateLocalDate = returnLocalDateFromUserInput(userToInput);
						if (toDateLocalDate.isAfter(fromDateLocalDate)) {
							break;
						} else {
							System.out.println("\n***DEPARTURE DATE MUST BE LATER THAN ARRIVAL DATE***\n");
						}
					} else {
						System.out.println("\n***INVALID YEAR ENTRY, PLEASE ENTER YEAR BETWEEN 2020 AND 2025****\n");
					}
				} else {
					System.out.println("\n***INVALID MONTH OR DAY, PLEASE TRY AGAIN***\n");
				}
			} 	handleNoSitesAvailable(); //PUSH TO NEXT MENU/METHOD
		}
	
	public void handleNoSitesAvailable() {
		List<Site> siteOptions = siteDAO.getAvailableSitesByCampgroundId(selectedCampground.getCampgroundId(),
				fromDateLocalDate, toDateLocalDate);
		if (siteOptions.size() == 0) {
			System.out.print(
					"\nThere are no available sites for the selected dates. Would you like to choose another campground "
							+ " and/or enter an alternate date range? (Y/N) >>> ");
			String alternateChoice = menu.getUserInput();
			if (alternateChoice.equalsIgnoreCase("Y")) {
				displayCampgroundOptions();
			}
			if (alternateChoice.equalsIgnoreCase("N")) {
				System.out.println("Goodbye!");
				System.exit(1);
			} else {
				System.out.println("\nYou've entered an incorrect selection. Returning you to campground menu.\n");
				displayCampgroundOptions();
			}
		} viewSitesAsSelectionFromCampgroundId(); //PUSH TO NEXT MENU/METHOD**
	}

	public void viewSitesAsSelectionFromCampgroundId() {
		List<Site> siteOptions = siteDAO.getAvailableSitesByCampgroundId(selectedCampground.getCampgroundId(),
				fromDateLocalDate, toDateLocalDate);

		System.out.println("\n***Available sites based on your campground selection and travel dates***");
		System.out.printf("\n%-15s %-15s %-15s %-15s %-15s %-10s", "Site No.", "Mac Occup.", "Accessible?",
				"Max RV Length", "Utilities?", "Cost");

		int dayDifference;
		String rvLength;
		double totalCost;

		Period period = Period.between(fromDateLocalDate, toDateLocalDate);
		dayDifference = period.getDays();
		double difference = (double) dayDifference;

		for (Site site : siteOptions) {
			totalCost = difference * selectedCampground.getDailyFee();
			if (site.getMaxRvLength() == 0) {
				rvLength = "N/A";
			} else {
				rvLength = Integer.toString(site.getMaxRvLength());
			}
			System.out.printf("\n%-15s %-15s %-15s %-15s %-15s %-10s", site.getSiteNumber(), site.getMaxOccupancy(),
					site.isAccessible(), rvLength, site.isUtilities(), String.format("$%.2f", totalCost));
		}
		System.out.print("\n\nWhich site would you like to reserve? (enter 0 to cancel) >>> ");
		Long choice = Long.parseLong(menu.getUserInput());
		if (choice == 0) {
			System.out.println("Returning you to main menu.\n");
			run();
		} else {
			selectedSiteNumber = choice;
			makeReservation(); //**PUSH TO NEXT MENU/METHOD
		}
	}

//HAVING SELECTED A CAMPGROUND, ARRIVAL AND DEPARTURE DATES, AND A SITE, MAKE RESERVATION	
	public void makeReservation() {
		System.out.print("What name would you like to make the reservation under? >>> ");
		String choice = menu.getUserInput();
		reservationDAO.bookReservation(selectedSiteNumber, choice, fromDateLocalDate, toDateLocalDate, LocalDate.now());
		System.out.print("\nWould you like to make another reservation? (Y/N) >>> ");
		String nextChoice = menu.getUserInput();
		if (nextChoice.equalsIgnoreCase("Y")) {
			displayParkOptions();
		}
		if (nextChoice.equalsIgnoreCase("N")) {
			System.out.println("Goodbye!");
			System.exit(1);
		}
	}

//END PROGRAM -- UTILITY METHODS BELOW**
	
//VALIDATES PROPER MONTH/DAY ENTRY AND PROPER FORMAT ENTERED BY USER AS BOOLEAN
	public boolean dateValidation(String dateEntered) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		@SuppressWarnings("unused")
		Date date = null;
		df.setLenient(false);

		try {
			date = df.parse(dateEntered);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

//VALIDATES PROPER YEAR FOR USER DATE INPUT
	public boolean checkYearOfDate(String userDateInput) {
		String[] checkYear = userDateInput.split("/");
		int intYear = Integer.parseInt(checkYear[2]);
		if (intYear < 2020 || intYear > 2025) {
			return false;
		} else {
			return true;
		}
	} //WHERE DO WE PUT TRY?CATCH LOOP FOR NON NUMERICAL ENTRY??***

//TAKES USER DATE INPUT AS STRING AND RETURNS LOCAL DATE
	public LocalDate returnLocalDateFromUserInput(String userDateInput) {
		LocalDate inputDate = null;
		String[] checkYear = userDateInput.split("/");
		int month = Integer.parseInt(checkYear[0]);
		int day = Integer.parseInt(checkYear[1]);
		int year = Integer.parseInt(checkYear[2]);
		inputDate = LocalDate.of(year, month, day);
		return inputDate;
	}

}
