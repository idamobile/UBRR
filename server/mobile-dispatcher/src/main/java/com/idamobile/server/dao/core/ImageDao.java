package com.idamobile.server.dao.core;

import java.util.Map;

import com.idamobile.protocol.ubrr.Commons.Platform;
import com.idamobile.protocol.ubrr.Commons.Resolution;

public interface ImageDao {
	
	Map<String, byte[]> get(Platform platform, Resolution resolution, String imageIds[]);
	
}
