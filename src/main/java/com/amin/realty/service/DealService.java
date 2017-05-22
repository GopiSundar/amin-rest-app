package com.amin.realty.service;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amin.realty.domain.DealDO;
import com.amin.realty.domain.WorkflowStageDO;
import com.amin.realty.repository.DealRepository;
import com.amin.realty.service.util.Result;

/**
 * Service class for managing deals.
 */
@Service
@Transactional
public class DealService {

	private final Logger log = LoggerFactory.getLogger(DealService.class);

	@Inject
	private DealRepository dealRepository;

	/*
	 * public DealService(DealRepository dealRepository) { this.dealRepository =
	 * dealRepository; }
	 */

	public Result createDeal(String tenantId, String propertyId, long buyerId) {

		Result result = null;

		String dealId = UUID.randomUUID().toString();
		result = dealRepository.saveDeal(dealId, tenantId, propertyId, buyerId);
		log.debug("Created Information for Deal: {}", result.getResult() ? result.getData() : result.getMessage());

		return result;
	}

	public Result pushToNextStage(String tenantId, String serverName, String dealId) {

		Result result = null;

		result = dealRepository.getDeals(tenantId, dealId,0,0);
		if (result.getResult()) {
			List<DealDO> list = (List<DealDO>) result.getData();
			if (list.isEmpty()) {
				result = new Result("Invalid deal id");
			} else {
				DealDO deal = list.get(0);
				result = WorkflowService.getNextWorkflowStage(serverName, deal.getStatusId());

				if (result.getResult()) {
					WorkflowStageDO nextWorkflowStage = (WorkflowStageDO) result.getData();
					result = dealRepository.updateStatus(dealId, nextWorkflowStage.getId(),nextWorkflowStage.getName());
				}
			}
		} else {
			result = new Result("Error occured when fetching deal information for update");
		}
		// result = dealRepository.updateDeal(id, status, brokerId);
		log.debug("Updated Information for Deal: {}", result.getResult() ? result.getData() : result.getMessage());

		return result;
	}

	public Result getDeals(String tenantId,String dealId, long buyerId, long brokerId) {

		Result result = null;

		result = dealRepository.getDeals(tenantId, dealId, buyerId, brokerId);
		log.debug("Get Information for Deal: {}", result.getResult() ? result.getData() : result.getMessage());

		return result;
	}

	/*
	 * public Result fetchProperties(String tenantId) {
	 * 
	 * Result result = propertyRepository.fetchProperties(tenantId); if
	 * (result.getResult()) { List<String> list = (List<String>)
	 * result.getData();
	 * 
	 * final JsonNodeFactory factory = JsonNodeFactory.instance;
	 * 
	 * ArrayNode array = factory.arrayNode();
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); list.forEach(property -> { try
	 * { array.add(mapper.readTree(property)); } catch (IOException e) {
	 * e.printStackTrace(); } });
	 * 
	 * result = new Result(true, null, array.toString());
	 * 
	 * }
	 * 
	 * return result;
	 * 
	 * }
	 */

}
