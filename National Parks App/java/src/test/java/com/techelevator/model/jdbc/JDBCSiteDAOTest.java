package com.techelevator.model.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Site;

public class JDBCSiteDAOTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCSiteDAO dao;
	
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
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	String sqlAddPark = "INSERT INTO park(park_id, name, location, establish_date, area, visitors, description) " +
						"VALUES(50, 'TEST', 'TEST', '2000-01-01', 5, 5, 'test test test')";
	jdbcTemplate.update(sqlAddPark);
	String sqlAddCampground = "INSERT INTO campground(campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) " +
								"VALUES(100, 50, 'Test', '01', '06', 30), (101, 50, 'Test2', '05', '12', 30)";
	jdbcTemplate.update(sqlAddCampground);
	String sqlAddSite = "INSERT INTO site(site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) " +
						"VALUES(5000, 100, 5000, 5, true, 0, true), (5001, 100, 5001, 5, false, 0, false), (5002, 101, 5002, 10, true, 0, true), (5003, 101, 5002, 10, false, 0, false)";
	jdbcTemplate.update(sqlAddSite);
	String sqlAddReservation = "INSERT INTO reservation(reservation_id, site_id, name, from_date, to_date, create_date) " +
								"VALUES(2000, 5000, 'TestRes', '2021-02-10', '2021-03-01', CURRENT_DATE), (2001, 5000, 'TestRes2', '2021-03-10', '2021-05-01', CURRENT_DATE),"
								+ " (2002, 5002, 'TestRes3', '2020-05-10', '2020-06-01', CURRENT_DATE), (2003, 5002, 'TestRes4', '2020-08-10', '2020-09-01', CURRENT_DATE)";
	jdbcTemplate.update(sqlAddReservation);
	dao = new JDBCSiteDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void get_available_sites_by_campground_id_and_dates() {
		Long exampleCampground = (long) 100;
		LocalDate exampleFromDate = new Date(2020-02-15).toLocalDate();
		LocalDate exampleToDate = new Date(2020-02-20).toLocalDate();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sqlSite = "SELECT COUNT(*) FROM site WHERE campground_id = ? AND site_id NOT IN (SELECT site.site_id "
				+ "FROM reservation INNER JOIN site ON site.site_id = reservation.site_id INNER JOIN campground "
				+ "ON campground.campground_id = site.campground_id WHERE site.campground_id = ? AND "
				+ "((? BETWEEN reservation.from_date AND reservation.to_date) OR (? BETWEEN reservation.from_date AND reservation.to_date) "
				+ "OR (reservation.from_date BETWEEN ? AND ?) OR (reservation.to_date BETWEEN ? AND ?)))";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSite, exampleCampground, exampleCampground, exampleFromDate, exampleToDate, exampleFromDate, exampleToDate, exampleFromDate, exampleToDate);	
		results.next();
		List<Site> siteList = dao.getAvailableSitesByCampgroundId(exampleCampground, exampleFromDate, exampleToDate);
		assertNotNull(siteList);
		assertEquals(results.getLong(1), siteList.size());
	}
	
	@Test
	public void get_available_sites_by_campground_id_and_dates_different_campground_expected_2() {
		Long exampleCampground = (long) 101;
		LocalDate exampleFromDate = new Date(2020-10-15).toLocalDate();//Why don't 08 and 09 work??? // why doesn't green show on JDBCDAO class when running coverage???
		LocalDate exampleToDate = new Date(2020-11-20).toLocalDate();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sqlSite = "SELECT COUNT(*) FROM site WHERE campground_id = ? AND site_id NOT IN (SELECT site.site_id "
				+ "FROM reservation INNER JOIN site ON site.site_id = reservation.site_id INNER JOIN campground "
				+ "ON campground.campground_id = site.campground_id WHERE site.campground_id = ? AND "
				+ "((? BETWEEN reservation.from_date AND reservation.to_date) OR (? BETWEEN reservation.from_date AND reservation.to_date) "
				+ "OR (reservation.from_date BETWEEN ? AND ?) OR (reservation.to_date BETWEEN ? AND ?)))";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSite, exampleCampground, exampleCampground, exampleFromDate, exampleToDate, exampleFromDate, exampleToDate, exampleFromDate, exampleToDate);
		results.next();
		List<Site> siteList = dao.getAvailableSitesByCampgroundId(exampleCampground, exampleFromDate, exampleToDate);
		assertNotNull(siteList);
		assertEquals(results.getLong(1), siteList.size());
	}
	
	@Test
	public void get_available_sites_by_park_id() {
		Long examplePark = (long) 50;
		LocalDate exampleFromDate = new Date(2020-10-15).toLocalDate();//Why don't 08 and 09 work??? // why doesn't green show on JDBCDAO class when running coverage???
		LocalDate exampleToDate = new Date(2020-11-20).toLocalDate();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sqlSite = "SELECT DISTINCT * FROM site WHERE campground_id = ? AND site_id NOT IN (SELECT site.site_id "
				+ "FROM reservation INNER JOIN site ON site.site_id = reservation.site_id INNER JOIN campground "
				+ "ON campground.campground_id = site.campground_id WHERE site.campground_id = ? AND "
				+ "((? BETWEEN reservation.from_date AND reservation.to_date) OR (? BETWEEN reservation.from_date AND reservation.to_date) "
				+ "OR (reservation.from_date BETWEEN ? AND ?) OR (reservation.to_date BETWEEN ? AND ?))) ORDER BY site.site_id LIMIT 5";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSite, examplePark, examplePark, exampleFromDate, exampleToDate, exampleFromDate, exampleToDate, exampleFromDate, exampleToDate);
		results.next();
		List<Site> siteList = dao.getAvailableSitesByParkId(examplePark, exampleFromDate, exampleToDate);
		assertNotNull(siteList);
		assertEquals(results.getLong(1), siteList.size());
	}
}

