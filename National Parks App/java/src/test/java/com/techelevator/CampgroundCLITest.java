package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;

public class CampgroundCLITest {

	BasicDataSource dataSource = new BasicDataSource();
	CampgroundCLI test = new CampgroundCLI(dataSource);
	
	@Test
	public void test_month_date_input_validation() {
		assertTrue(test.dateValidation("07/21/1992"));
		assertTrue(test.dateValidation("05/22/2020"));
		assertTrue(test.dateValidation("5/2/2025"));
		assertTrue(test.dateValidation("05/22/25"));
		assertFalse(test.dateValidation("15/21/1992"));
		assertFalse(test.dateValidation("07/45/1992"));
		assertFalse(test.dateValidation("07-21-1992"));
		assertFalse(test.dateValidation("A-21-1992"));
		assertFalse(test.dateValidation("07/A/1992"));
		assertFalse(test.dateValidation("07/21/A"));
		assertTrue(test.dateValidation("07/21/2A00"));
		assertTrue(test.dateValidation("07/21/200"));
		assertTrue(test.dateValidation("07/21/2A"));
		assertFalse(test.dateValidation("07/21"));
		assertFalse(test.dateValidation("07/21/"));
	}

	@Test
	public void test_year_input_validation() {
		assertFalse(test.checkYearOfDate("07/21/1992"));
		assertFalse(test.checkYearOfDate("07/21/2026"));
		assertTrue(test.checkYearOfDate("07/21/2021"));
		assertFalse(test.checkYearOfDate("07/21/19924543"));
		assertTrue(test.checkYearOfDate("07/21/2025"));
		assertFalse(test.checkYearOfDate("07/21/2A00"));
	} //CHECK TRY CATCH LOOP FOR YEAR LETTER INPUT ISSUES

	@Test
	public void test_return_local_date_from_string() throws ParseException {
		DateFormat df = new SimpleDateFormat("MM/dd/yyy");
		java.util.Date date = df.parse("07/21/2020");
		assertEquals(date, test.returnLocalDateFromUserInput("07/21/2020"));
	}
}

