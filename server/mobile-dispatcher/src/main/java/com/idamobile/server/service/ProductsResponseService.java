package com.idamobile.server.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Partners.ProductRequest;
import com.idamobile.protocol.ubrr.Partners.ProductResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.dao.core.ProductDao;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class ProductsResponseService extends AbstractMessageService<ProductRequest>{

	@Autowired
	private ProductDao productDao;
	
	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasProductsRequest();
	}

	@Override
	protected ProductRequest extractRequestMessage(MBSRequest request) {
		return request.getProductsRequest();
	}

	@Override
	protected void processMessage(ProductRequest request, Builder responseBuilder) {		
		ProductResponse.Builder builder = ProductResponse.newBuilder();
		
		Set<String> existing = new HashSet<String>(request.getExistingList());		
		
		for (String product: productDao.all()) {
			if (!existing.contains(product))
				builder.addProducts(product);
			existing.remove(product);
		}
		
		builder.addAllDeleted(existing);		
		responseBuilder.setProductsResponse(builder.build());
		
	}

}
