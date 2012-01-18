package com.idamobile.server.dao.core;

public interface UpdatesDao {

	public static final String ENTITY_CONTACTS = "contacts";
	public static final String ENTITY_BANNERS = "banners";

	public static final String ENTITY_LOC_OFFICES = "locations.offices";
	public static final String ENTITY_LOC_ATMS = "locations.atms";
	public static final String ENTITY_LOC_CREDIT_POINTS = "locations.credit.points";
	
	public static final String ENTITY_HC_STATUSES = "statuses";
	public static final String ENTITY_HC_TYPES = "productTypes";
	public static final String ENTITY_HC_CATEGORIES = "payment.categories";
	public static final String ENTITY_HC_BENEFICIARIES = "beneficiaries";
	public static final String ENTITY_HC_DISCOUNT_CATEGORIES = "discount.categories";

	long getUpdateTime(String entityName);
	
	void saveLastUpdateTime(String entityName, long time);
	
	void saveLastUpdateTimeIsNow(String entityName);
}
