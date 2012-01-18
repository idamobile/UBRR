package com.idamobile.server.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Currency.CurrencyRateRequest;
import com.idamobile.protocol.ubrr.Currency.CurrencyRateResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.dao.core.CurrencyRateDao;
import com.idamobile.server.model.CurrencyRate;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class CurrencyRateResponseService extends AbstractMessageService<CurrencyRateRequest>{

	@Autowired
	private CurrencyRateDao currencyDao;
	
	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasCurrencyRequest();
	}

	@Override
	protected CurrencyRateRequest extractRequestMessage(MBSRequest request) {
		return request.getCurrencyRequest();
	}

	@Override
	protected void processMessage(CurrencyRateRequest request, Builder responseBuilder) {
		CurrencyRateResponse.Builder builder = CurrencyRateResponse.newBuilder();
		
		for (CurrencyRate rate: currencyDao.all()) {
			builder.addCurrency(rate.createMessage());
		}
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		
		builder.setLastUpdateTime(c.getTimeInMillis());
		
		responseBuilder.setCurrencyResponse(builder.build());		
	}

}
