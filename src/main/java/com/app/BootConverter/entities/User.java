package com.app.BootConverter.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "login")
	private String login;
	@Column(name = "password")
	private String password;

	public User(Long id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
	}

	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public User(Long id, String login) {
		this.id = id;
		this.login = login;
	}
}
