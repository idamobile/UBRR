package models.core;

import play.db.jpa.JPABase;
import play.db.jpa.Model;

public class UpdateTimeAwareModel extends Model{
	
	protected String ENTITY_NAME = "UpdateTimeAwareModel";
	
	@Override
	public <T extends JPABase> T save() {
		saveLastUpdateTime();
		return super.save();
	}
	
	public void saveLastUpdateTime() {
		LastUpdate.setNow(ENTITY_NAME);
	}

}
