package com.app.BootConverter.services;

import com.app.BootConverter.entities.Currency;

import java.util.List;


public interface CurrencyConverterService {

	String getName(String id);

	Integer getNominal(Long id);

	Double getValue(Long id);

	List<Currency> getAll();

	Double convert(String amount, String from, String to);

	void updateCurrency();
}
