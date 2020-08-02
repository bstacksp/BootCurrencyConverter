package com.app.BootConverter.repositories;

import com.app.BootConverter.entities.Currency;
import com.app.BootConverter.entities.History;
import com.app.BootConverter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
	List<History> findByUserId(User user);
	List<History> findByFromCurrencyIdAndToCurrencyId(Currency fromCurrency, Currency toCurrency);
}
