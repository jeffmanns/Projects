package com.techelevator.model.jdbc;

import static org.junit.Assert.*;

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

import com.techelevator.Park;

public class JDBCParkDAOTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCParkDAO dao;
	
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
	dao = new JDBCParkDAO(dataSource);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void get_all_parks() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sqlPark = "SELECT COUNT(*) FROM park";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlPark);
		results.next();
		List<Park> resultList = dao.getAllParks();
		assertNotNull(resultList);
		assertEquals(results.getInt(1), resultList.size());
	}
}
