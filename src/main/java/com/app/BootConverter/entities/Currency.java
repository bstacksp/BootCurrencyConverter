package com.app.BootConverter.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "currencies")
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	String name;

	@Column(name = "char_code")
	String charCode;

	@Column(name = "nominal")
	Long nominal;

	@Column(name = "value")
	Double value;

	public Currency(String name, String charCode, Long nominal, Double value) {
		this.name = name;
		this.charCode = charCode;
		this.nominal = nominal;
		this.value = value;
	}
}
