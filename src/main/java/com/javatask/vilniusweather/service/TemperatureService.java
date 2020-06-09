package com.javatask.vilniusweather.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.javatask.vilniusweather.climacellapi.ClimacellClient;
import com.javatask.vilniusweather.dao.TemperatureRepository;
import com.javatask.vilniusweather.model.Temperature;

@Service
public class TemperatureService {
	
	@Autowired
	TemperatureRepository temperatureRepository;
	
	@Autowired
	ClimacellClient climacellClient;
	
	@PostConstruct
	public void postConstruct() {
		saveLastMonthTemperature();
	}
	
	public void saveLastMonthTemperature() {
		temperatureRepository.saveAll(climacellClient.getLastMonthTemperature());
	}
	
	@Scheduled(cron = "0 20 * * * ?")
	public void saveTemperatureEveryHour() {
		temperatureRepository.save(climacellClient.getCurrentTemperature());
	}
	
	
	

}
