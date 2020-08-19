package com.app.BootConverter.services.impl;

import com.app.BootConverter.entities.Currency;
import com.app.BootConverter.repositories.CurrenciesRepository;
import com.app.BootConverter.services.CurrencyConverterService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

	Logger log = LoggerFactory.getLogger(CurrencyConverterServiceImpl.class);

	@Autowired
	CurrenciesRepository currenciesRepository;

	@Value("${centrabank.url}")
	private String centrabankUrl;

	@Override
	public String getName(String id) {
		return currenciesRepository.getOne(Long.parseLong(id)).getName();
	}

	@Override
	public Long getNominal(Long id) {
		return currenciesRepository.getOne(id).getNominal();
	}

	@Override
	public Double getValue(Long id) {
		return currenciesRepository.getOne(id).getValue();
	}

	@Override
	public List<Currency> getAll() {
		return currenciesRepository.findAll();
	}

	@Override
	public Double convert(String amount, String from, String to) {

		log.info("Currency's value = " +  getValue(Long.parseLong(from)));
		return (Double.parseDouble(amount)
				* getValue(Long.parseLong(from))
				/ getNominal(Long.parseLong(from))
				/ getValue(Long.parseLong(to))
				* getNominal(Long.parseLong(to)));
	}

	@Scheduled(fixedDelayString = "${currency.update.delay}")
	@Override
	public void updateCurrency() {
		try {
			Document doc = Jsoup.connect(centrabankUrl)
					.parser(Parser.xmlParser()).get();
			Elements elements = doc.getElementsByTag("Valute");
			if (currenciesRepository.findById(1L).isEmpty()) {
				Currency Ruble = new Currency("Российский рубль", "RUR", 1L, 1.0);
				currenciesRepository.save(Ruble);
			}
			long i = 2;
			for (Element e : elements) {
				Optional<Currency> currencyToUpdate = currenciesRepository.findById(i);
				i++;
				if(currencyToUpdate.isEmpty()) {
					String charCode = e.child(1).text();
					String name = e.child(3).text();
					Long nominal = Long.parseLong(e.child(2).text());
					Double value = Double.parseDouble(e.child(4).text().replace(",", "."));
					Currency currency = new Currency(name, charCode, nominal, value);
					currenciesRepository.save(currency);
				}
				else {
					currencyToUpdate.get().setValue(Double.parseDouble(e.child(4).text().replace(",", ".")));
					currenciesRepository.save(currencyToUpdate.get());
				}

			}
			log.info("Updated currencies values");
		}
		catch (IOException e) {
			log.error("Error during parsing currencies values", e);
		}
	}
}
