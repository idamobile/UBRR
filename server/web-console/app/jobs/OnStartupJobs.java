package jobs;

import models.core.Property;
import play.db.jpa.Blob;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class OnStartupJobs extends Job{

	@Override
	public void doJob() throws Exception {
		Property.set(Property.ATTACHMENTS_PATH, Blob.getStore().getAbsolutePath()+'/');
	}
	
	
}
