package com.amin.realty.service;

import java.util.HashMap;
import java.util.Map;

public class TenantService {
	private static final Map<String, String> tenantMap = new HashMap<String, String>();

	static {
		// TODO - get from definition table
		tenantMap.put("localhost", "65ad46c8-0c56-466b-b94d-1a5427edb1e8");
	}

	private TenantService(){		
	}
	
	public static String getTenantId(String serverName) {
		return tenantMap.get(serverName);
	}
}
