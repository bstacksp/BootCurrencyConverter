package com.app.BootConverter.services;

import com.app.BootConverter.entities.User;
import com.app.BootConverter.entities.dto.HistoryFormDto;

import java.util.List;

public interface HistoryService {

	List<HistoryFormDto> getHistoryUser(User user);

	void saveToHistory(User user, String fromCurrency, String toCurrency);
}
