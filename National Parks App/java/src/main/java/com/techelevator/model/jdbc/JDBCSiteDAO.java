package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Site;
import com.techelevator.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Site> getAvailableSitesByCampgroundId(Long campgroundId, LocalDate startDate, LocalDate endDate) {
		List<Site> siteList = new ArrayList<>();
		String sqlSite = "SELECT DISTINCT * FROM site WHERE campground_id = ? AND site_id NOT IN (SELECT site.site_id "
				+ "FROM reservation INNER JOIN site ON site.site_id = reservation.site_id INNER JOIN campground "
				+ "ON campground.campground_id = site.campground_id WHERE site.campground_id = ? AND "
				+ "((? BETWEEN reservation.from_date AND reservation.to_date) OR (? BETWEEN reservation.from_date AND reservation.to_date) "
				+ "OR (reservation.from_date BETWEEN ? AND ?) OR (reservation.to_date BETWEEN ? AND ?))) ORDER BY site.site_id LIMIT 5";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSite, campgroundId, campgroundId, startDate, endDate, startDate, endDate, startDate, endDate);
		while (results.next()) {
			Site site = mapRowToSite(results);
			siteList.add(site);
		}
		return siteList;
	}

	@Override
	public List<Site> getAvailableSitesByParkId(Long campgroundId, LocalDate startDate, LocalDate endDate) {
		List<Site> siteList = new ArrayList<>();
		String sqlSite = "SELECT DISTINCT * FROM site WHERE campground_id = ? AND site_id NOT IN (SELECT site.site_id "
				+ "FROM reservation INNER JOIN site ON site.site_id = reservation.site_id INNER JOIN campground "
				+ "ON campground.campground_id = site.campground_id WHERE site.campground_id = ? AND "
				+ "((? BETWEEN reservation.from_date AND reservation.to_date) OR (? BETWEEN reservation.from_date AND reservation.to_date) "
				+ "OR (reservation.from_date BETWEEN ? AND ?) OR (reservation.to_date BETWEEN ? AND ?))) ORDER BY site.site_id LIMIT 5";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSite, campgroundId, campgroundId, startDate, endDate, startDate, endDate, startDate, endDate);
		while (results.next()) {
			Site site = mapRowToSite(results);
			siteList.add(site);
		}
		return siteList;
	}
	
	public Site mapRowToSite(SqlRowSet results) {
		Site site;
		site = new Site();
		site.setSiteId(results.getLong("site_id"));
		site.setCampgroundId(results.getLong("campground_id"));
		site.setSiteNumber(results.getLong("site_number"));
		site.setMaxOccupancy(results.getLong("max_occupancy"));
		site.setAccessible(results.getBoolean("accessible"));
		site.setMaxRvLength(results.getInt("max_rv_length"));
		site.setUtilities(results.getBoolean("utilities")); 
		return site;
	}

	
	
}
