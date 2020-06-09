package com.javatask.vilniusweather.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Temperature {
	
	@Id
	@GeneratedValue
	private int id;
	
	private int value;
	
	private LocalDateTime observationTime;
	
	public Temperature(int value, LocalDateTime observationTime) {
		this.value = value;
		this.observationTime = observationTime;
	}

}
