package com.techelevator.model.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Campground;

public class JDBCCampgroundDAOTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCCampgroundDAO dao;
	
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
								"VALUES(100, 50, 'Test', '01', '02', 30), (101, 50, 'Test2', '02', '03', 30)";
	jdbcTemplate.update(sqlAddCampground);
	dao = new JDBCCampgroundDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void get_campgrounds_by_park_id() {
		Long examplePark = (long) 50;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sqlCampground = "SELECT COUNT(*) FROM campground WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCampground, examplePark);	
		results.next();
		List<Campground> campgroundList = dao.getCampgroundsByParkId(examplePark);
		assertNotNull(campgroundList);
		assertEquals(results.getInt(1), campgroundList.size());
	}

}
