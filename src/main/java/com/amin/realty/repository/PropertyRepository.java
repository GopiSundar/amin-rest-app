package com.amin.realty.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.amin.realty.service.util.Result;

@Repository
public class PropertyRepository {
	private final JdbcTemplate jdbcTemplate;

	private static final String PROC_SAVE_PROPERTY = "call proc_save_property(?,?,?,?)";
	private static final String PROC_UPDATE_PROPERTY = "call proc_update_property(?,?,?,?)";
	private static final String PROC_GET_PROPERTY = "proc_get_property";

	@Inject
	public PropertyRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * To create a new property
	 * 
	 */
	public Result saveProperty(String tenantId, String propertyId, String metadata) {

		Result result = null;

		try {
			// Get Connection from dataSource
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement callableSt = connection.prepareCall(PROC_SAVE_PROPERTY);
			callableSt.setString(1, propertyId);
			callableSt.setString(2, tenantId);
			callableSt.setString(3, metadata);
			callableSt.registerOutParameter(4, Types.VARCHAR);

			// Call Stored Procedure
			callableSt.executeUpdate();
			result = new Result(true, null, callableSt.getString(4));

		} catch (SQLException e) {
			e.printStackTrace();
			result = new Result(e.getMessage());
		}

		return result;
	}

	/**
	 * To update an existing property
	 * 
	 * @return
	 */
	public Result updateProperty(String tenantId, String propertyId, String metadata) {

		Result result = null;

		try {
			// Get Connection from dataSource
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement callableSt = connection.prepareCall(PROC_UPDATE_PROPERTY);
			callableSt.setString(1, propertyId);
			callableSt.setString(2, tenantId);
			callableSt.setString(3, metadata);
			callableSt.registerOutParameter(4, Types.VARCHAR);

			// Call Stored Procedure
			callableSt.executeUpdate();
			String updatedJson = callableSt.getString(4);
			if (updatedJson != null) {
				result = new Result(true, null, callableSt.getString(4));
			} else {
				result = new Result("Property not found");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			result = new Result(e.getMessage());
		}

		return result;
	}

	/**
	 * To get property lis
	 * 
	 * @return
	 */
	public Result fetchProperties(String tenantId) {

		Result result = null;

		/*
		 * try { // Get Connection from dataSource Connection connection =
		 * jdbcTemplate.getDataSource().getConnection(); CallableStatement
		 * callableSt = connection.prepareCall(PROC_GET_PROPERTY);
		 * callableSt.setString(1, tenantId); callableSt.registerOutParameter(2,
		 * Types.VARCHAR);
		 * 
		 * // Call Stored Procedure callableSt.executeUpdate(); String jsonArray
		 * = callableSt.getString(2); if (jsonArray != null) { result = new
		 * Result(true, null, callableSt.getString(4)); } else { result = new
		 * Result("No properties found"); }
		 * 
		 * } catch (SQLException e) { e.printStackTrace(); result = new
		 * Result(e.getMessage()); }
		 */
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(PROC_GET_PROPERTY)
				.returningResultSet("properties", new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString(1);
					}
				});

		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("in_tenantid", tenantId);

		Map<String, Object> resultMap = jdbcCall.execute(inParams);

		List<String> list = (List<String>) resultMap.get("properties");

		if (list != null) {
			result = new Result(true, null, list);
		} else {
			result = new Result("No properties found");
		}
		return result;
	}

}
