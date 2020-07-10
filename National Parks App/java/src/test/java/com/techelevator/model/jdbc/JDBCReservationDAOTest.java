package com.techelevator.model.jdbc;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import junit.framework.Assert;

public class JDBCReservationDAOTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCReservationDAO dao;

	@BeforeClass
	public static void setUpDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws SQLException {
		dataSource.destroy();
	}

	@Before
	public void setup() {
		dao = new JDBCReservationDAO(dataSource);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sqlAddPark = "INSERT INTO park(park_id, name, location, establish_date, area, visitors, description) "
				+ "VALUES(50, 'TEST', 'TEST', '2000-01-01', 5, 5, 'test test test')";
		jdbcTemplate.update(sqlAddPark);
		String sqlAddCampground = "INSERT INTO campground(campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) "
				+ "VALUES(100, 50, 'Test', '01', '06', 30), (101, 50, 'Test2', '05', '12', 30)";
		jdbcTemplate.update(sqlAddCampground);
		String sqlAddSite = "INSERT INTO site(site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) "
				+ "VALUES(5000, 100, 5000, 5, true, 0, true), (5001, 100, 5001, 5, false, 0, false), (5002, 101, 5002, 10, true, 0, true), (5003, 101, 5002, 10, false, 0, false)";
		jdbcTemplate.update(sqlAddSite);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void test_book_reservation_return_id() {
		LocalDate exampleFromDate = new Date(2020 - 02 - 15).toLocalDate();
		LocalDate exampleToDate = new Date(2020 - 02 - 20).toLocalDate();
		LocalDate createDate = LocalDate.now();
		dao.bookReservation((long) 5000, "Michael Jackson", exampleFromDate, exampleToDate, createDate);

	}

}
