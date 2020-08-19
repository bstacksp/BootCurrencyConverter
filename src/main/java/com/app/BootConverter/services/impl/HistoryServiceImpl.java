package com.app.BootConverter.services.impl;

import com.app.BootConverter.entities.History;
import com.app.BootConverter.entities.User;
import com.app.BootConverter.entities.dto.HistoryFormDto;
import com.app.BootConverter.repositories.CurrenciesRepository;
import com.app.BootConverter.repositories.HistoryRepository;
import com.app.BootConverter.repositories.UsersRepository;
import com.app.BootConverter.services.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

	Logger log = LoggerFactory.getLogger(HistoryServiceImpl.class);

	@Autowired
	CurrenciesRepository currenciesRepository;

	@Autowired
	HistoryRepository historyRepository;

	@Autowired
	UsersRepository usersRepository;

	@Override
	public List<HistoryFormDto> getHistoryUser(User user) {
		List<History> historyList = historyRepository.findByUserId(usersRepository.getByLogin(user.getLogin()));
		List<HistoryFormDto> historyFormDtoList = new ArrayList<>();
		for (History his : historyList) {
			HistoryFormDto history = new HistoryFormDto(
					his.getFromCurrencyId().getCharCode(),
					his.getToCurrencyId().getCharCode(),
					his.getValue(),
					his.getDate());
			historyFormDtoList.add(history);
		}
		return historyFormDtoList;
	}

	@Override
	public void saveToHistory(User user, String fromCurrency,
							  String toCurrency) {
		Date date = new Date();
		History newNote = new History(usersRepository.getByLogin(user.getLogin()),
				currenciesRepository.getOne(Long.parseLong(fromCurrency)),
				currenciesRepository.getOne(Long.parseLong(toCurrency)),
				currenciesRepository.getOne(Long.parseLong(fromCurrency)).getValue()
						/ currenciesRepository.getOne(Long.parseLong(fromCurrency)).getNominal()
						/ currenciesRepository.getOne(Long.parseLong(toCurrency)).getValue()
						* currenciesRepository.getOne(Long.parseLong(toCurrency)).getNominal(), date);
		List<History> notes = historyRepository.findByFromCurrencyIdAndToCurrencyId(
				currenciesRepository.getOne(Long.parseLong(fromCurrency)),
				currenciesRepository.getOne(Long.parseLong(toCurrency)));

		boolean flag = true;
		DecimalFormat df = new DecimalFormat("#.#####");
		for (History oldNote : notes) {
			log.info("{} {}", df.format(oldNote.getValue()), df.format(newNote.getValue()));
			if (df.format(oldNote.getValue()).equals(df.format(newNote.getValue()))
					&& df.format(oldNote.getValue()).equals(df.format(newNote.getValue())))
				flag = false;
			log.info(flag + "Value = " + newNote.getValue());
		}
		if (flag) {
			historyRepository.save(newNote);
			log.info("Save new note to history");
		}
	}
}
