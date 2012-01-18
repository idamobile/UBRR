package com.idamobile.server.service.locations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Partners.ClusterMessage;
import com.idamobile.protocol.ubrr.Partners.MapPartnersRequest;
import com.idamobile.protocol.ubrr.Partners.MapPartnersResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.dao.core.locations.PartnerDao;
import com.idamobile.server.model.locations.GeoPoint;
import com.idamobile.server.model.locations.Partner;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class MapPartnersResponseService extends AbstractMessageService<MapPartnersRequest>{

	@Value("${idaserver.cluster.maxSize}")
	private int clusterMaxSize;
	
	@Value("${idaserver.cluster.cellsX}")
	private int cellsX;
	
	@Value("${idaserver.cluster.cellsY}")
	private int cellsY;	
	
	@Autowired
	private PartnerDao partnerDao;
	
	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasMapPartnersRequest();
	}

	@Override
	protected MapPartnersRequest extractRequestMessage(MBSRequest request) {
		return request.getMapPartnersRequest();
	}

	@Override
	protected void processMessage(MapPartnersRequest request, Builder responseBuilder) {
		MapPartnersResponse.Builder builder = MapPartnersResponse.newBuilder();
		
		double top = request.getTopLeft().getLatitude();
		double left = request.getTopLeft().getLongitude();
		double dx = (request.getBottomRight().getLatitude()-top)/cellsX;
		double dy = (request.getBottomRight().getLongitude()-left)/cellsY;
		
		for (int x = 0; x<cellsX; x++) {
			for (int y = 0; y<cellsY; y++) {
				GeoPoint tl = new GeoPoint(top+dx*x, left+dy*y);
				GeoPoint br = new GeoPoint(top+dx*(x+1), left+dy*(y+1));
				List<Partner> partners = partnerDao.getViewportPartners(tl, br);
				if (partners.size() > clusterMaxSize) {
					double centerX = 0.0;
					double centerY = 0.0;
					for (Partner p: partners) {
						centerX += p.getLatitude();
						centerY += p.getLongitude();
					}
					centerX /= partners.size();
					centerY /= partners.size();
					
					ClusterMessage.Builder cluster = ClusterMessage.newBuilder();
					cluster.setCenter(new GeoPoint(centerX, centerY).asMessage());
					cluster.setTopLeft(tl.asMessage());
					cluster.setBottomRight(br.asMessage());
					cluster.setItemsCount(partners.size());
					
					builder.addClusters(cluster.build());
				}else {
					for (Partner p: partners) {
						builder.addPartners(p.createMessage());
					}
				}
				
			}
		}		
		
		responseBuilder.setMapPartnersResponse(builder.build());
		
	}

}
