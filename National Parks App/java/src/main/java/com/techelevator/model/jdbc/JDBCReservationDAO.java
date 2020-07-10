package com.techelevator.model.jdbc;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void bookReservation(Long siteId, String name, LocalDate startDate, LocalDate endDate, LocalDate createDate) {
		String sqlInsertReservation = "INSERT INTO reservation(site_id, name, from_date, to_date, create_date) " +
				"VALUES(?, ?, ?, ?, ?) RETURNING reservation_id";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlInsertReservation, siteId, name, startDate, endDate, createDate);
		result.next();
		
		System.out.println("\n**The reservation has been made and the confirmation_id is {" + result.getLong(1) + "}**");
		
	}

}
