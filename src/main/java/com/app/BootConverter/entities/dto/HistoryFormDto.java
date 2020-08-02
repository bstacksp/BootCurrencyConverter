package com.app.BootConverter.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class HistoryFormDto {
	private String fromStr;
	private String toStr;
	private Double result;
	private Date date;

}
