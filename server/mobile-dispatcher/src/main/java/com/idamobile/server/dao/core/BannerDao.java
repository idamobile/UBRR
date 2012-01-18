package com.idamobile.server.dao.core;

import java.util.List;

import com.idamobile.protocol.ubrr.Commons.Platform;
import com.idamobile.protocol.ubrr.Commons.Resolution;
import com.idamobile.server.model.Banner;

public interface BannerDao {
		
	List<Banner> get();
	
	int count();
	
}
