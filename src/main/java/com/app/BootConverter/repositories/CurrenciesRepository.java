package com.app.BootConverter.repositories;

import com.app.BootConverter.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrenciesRepository extends JpaRepository<Currency, Long> {

}
