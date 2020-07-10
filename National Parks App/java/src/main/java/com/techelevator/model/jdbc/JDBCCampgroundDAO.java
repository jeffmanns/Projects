package com.techelevator.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Campground;
import com.techelevator.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Campground> getCampgroundsByParkId(Long parkId) {
		List<Campground> campgroundList = new ArrayList<>();
		String sqlCampground = "SELECT * FROM campground WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCampground, parkId);
		while (results.next()) {
		Campground campgrounds = mapRowToCampground(results);
		campgroundList.add(campgrounds);
		}
		return campgroundList;
	}

	public Campground mapRowToCampground(SqlRowSet results) {
		Campground campground;
		campground = new Campground();
		campground.setCampgroundId(results.getLong("campground_id"));
		campground.setParkId(results.getLong("park_id"));
		campground.setName(results.getString("name"));
		campground.setOpenFrom(results.getInt("open_from_mm"));
		campground.setOpenTo(results.getInt("open_to_mm"));
		campground.setDailyFee(results.getDouble("daily_fee"));
		return campground;
	}
	
}
