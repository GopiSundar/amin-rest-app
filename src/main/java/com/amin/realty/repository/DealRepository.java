package com.amin.realty.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.amin.realty.domain.DealDO;
import com.amin.realty.service.util.Result;

@Repository
public class DealRepository {

	private final JdbcTemplate jdbcTemplate;

	private static final String PROC_SAVE_DEAL = "proc_save_deal";
	private static final String PROC_GET_DEALSTATUS = "proc_update_dealstatus";
	private static final String PROC_GET_DEAL = "proc_get_deal";

	@Inject
	public DealRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * To create a new deal
	 * 
	 */
	public Result saveDeal(String dealId, String tenantId, String propertyId, long buyerId) {

		Result result = null;

		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("in_id", dealId);
		inParams.addValue("in_tenantid", tenantId);
		inParams.addValue("in_propertyid", propertyId);
		inParams.addValue("in_buyerid", buyerId);

		try {
			Map<String, Object> resultMap = new SimpleJdbcCall(jdbcTemplate).withProcedureName(PROC_SAVE_DEAL)
					.returningResultSet("output", new RowMapper<String>() {
						@Override
						public String mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getString(1);
						}
					}).execute(inParams);

			result = new Result(true, null, resultMap.get("output"));

		} catch (Exception e) {
			e.printStackTrace();
			result = new Result("Error occured while saving the deal");
		}

		return result;
	}
	
	/**
	 * To update a deal status
	 * 
	 */
	public Result updateStatus(String dealId, int newWorkflowId, String newWorkflowName) {

		Result result = null;

		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("in_id", dealId);
		inParams.addValue("in_statusid", newWorkflowId);
		inParams.addValue("in_statusname", newWorkflowName);

		try {
			Map<String, Object> resultMap = new SimpleJdbcCall(jdbcTemplate).withProcedureName(PROC_GET_DEALSTATUS)
					.returningResultSet("output", new RowMapper<DealDO>() {
						@Override
						public DealDO mapRow(ResultSet rs, int rowNum) throws SQLException {
							DealDO deal = new DealDO();

							deal.setId(rs.getString("id"));
							deal.setTenantId(rs.getString("tenant_id"));
							deal.setPropertyId(rs.getString("property_id"));
							deal.setBuyerId(rs.getLong("buyer_id"));
							deal.setBrokerId(rs.getLong("broker_id"));
							deal.setNdaId(rs.getString("nda_id"));
							deal.setStatusId(rs.getInt("status_id"));
							deal.setStatusName(rs.getString("status_name"));

							return deal;						}
					}).execute(inParams);

			result = new Result(true, null, resultMap.get("output"));

		} catch (Exception e) {
			e.printStackTrace();
			result = new Result("Error occured while saving the deal");
		}

		return result;
	}

	/**
	 * To get deal list
	 * 
	 * @return
	 */
	public Result getDeals(String tenantId,String dealId, long buyerId, long brokerId) {

		Result result = null;

		MapSqlParameterSource inParams = new MapSqlParameterSource();
		inParams.addValue("in_dealid", dealId);
		inParams.addValue("in_tenantid", tenantId);
		inParams.addValue("in_buyerid", buyerId);
		inParams.addValue("in_brokerid", brokerId);


		try {
			Map<String, Object> resultMap = new SimpleJdbcCall(jdbcTemplate).withProcedureName(PROC_GET_DEAL)
					.returningResultSet("dealOutput", new RowMapper<DealDO>() {
						@Override
						public DealDO mapRow(ResultSet rs, int rowNum) throws SQLException {
							DealDO deal = new DealDO();

							deal.setId(rs.getString("id"));
							deal.setTenantId(rs.getString("tenant_id"));
							deal.setPropertyId(rs.getString("property_id"));
							deal.setBuyerId(rs.getLong("buyer_id"));
							deal.setBrokerId(rs.getLong("broker_id"));
							deal.setNdaId(rs.getString("nda_id"));
							deal.setStatusId(rs.getInt("status_id"));
							deal.setStatusName(rs.getString("status_name"));

							return deal;
						}
					}).execute(inParams);

			List<DealDO> list = (List<DealDO>) resultMap.get("dealOutput");
			result = new Result(true, null, list);

		} catch (Exception e) {
			e.printStackTrace();
			result = new Result("Error occured while fetching the deals");
		}

		return result;
	}
}
