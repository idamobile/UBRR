package com.idamobile.server.model;

import com.idamobile.protocol.ubrr.Currency.CurrencyMessage;
import com.idamobile.protocol.ubrr.Currency.CurrencyOperation;

/**
 * Represents items in currency exchange rate functionality.
 * @author zjor
 *
 */
public class CurrencyRate {
	
    /**
     * Operation type code.
     * 		BUY = 0;
     *		SELL = 1;
     *		CENTRAL BANK = 2;
     */
	private CurrencyOperation operation;
	
	/**
	 * ISO 4217 Currency Code. Examples:
	 * 810 - RUR
	 * 978 - EUR
	 * 840 - USD
	 */
	private int currencyCode;
	
	/**
	 * Price for amount of currency in RUR
	 */
	private double price;
	
	/**
	 * Number of foreign currency units
	 */
	private double amount;
	
	/**
	 * Changes delta
	 */
	private double delta;

	public CurrencyRate(CurrencyOperation operation, int currencyCode, double price, double amount,
			double delta) {
		this.operation = operation;
		this.currencyCode = currencyCode;
		this.price = price;
		this.amount = amount;
		this.delta = delta;
	}

	public CurrencyMessage createMessage() {
		CurrencyMessage.Builder builder = CurrencyMessage.newBuilder()		
			.setAmount(amount)
			.setCurrency(currencyCode)
			.setPrice(price)
			.setDelta(delta);
		
		if (operation != null) {
			builder.setOperation(operation);
		}
		
		return builder.build();
	}
	
	
}
