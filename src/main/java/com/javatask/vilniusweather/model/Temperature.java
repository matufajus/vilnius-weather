package com.javatask.vilniusweather.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.javatask.vilniusweather.utils.LocalDateTimeSerializer;
import com.javatask.vilniusweather.utils.TemperatureDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@JsonDeserialize(using = TemperatureDeserializer.class)
public class Temperature {
	
	@Id
	@GeneratedValue
	private int id;
	
	private int value;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)  
	private LocalDateTime observationTime;
	
	public Temperature(int value, LocalDateTime observationTime) {
		this.value = value;
		this.observationTime = observationTime;
	}

}
