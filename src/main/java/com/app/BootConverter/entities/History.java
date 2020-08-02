package com.app.BootConverter.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "history")
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User userId;

	@ManyToOne
	@JoinColumn(name = "from_currency_id")
	private Currency fromCurrencyId;

	@ManyToOne
	@JoinColumn(name = "to_currency_id")
	private Currency toCurrencyId;

	@Column(name = "value")
	private Double value;
	@Column(name = "date")
	private Date date;

	public History(User userId, Currency fromCurrencyId, Currency toCurrencyId, Double value, Date date) {
		this.userId = userId;
		this.fromCurrencyId = fromCurrencyId;
		this.toCurrencyId = toCurrencyId;
		this.value = value;
		this.date = date;
	}

}
