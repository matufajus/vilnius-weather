package com.javatask.vilniusweather.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.javatask.vilniusweather.climacellapi.ClimacellClient;
import com.javatask.vilniusweather.dao.TemperatureRepository;
import com.javatask.vilniusweather.model.Temperature;

import lombok.extern.java.Log;
import net.bytebuddy.description.annotation.AnnotationDescription.Loadable;

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

	private void saveLastMonthTemperature() {
		temperatureRepository.saveAll(climacellClient.getLastMonthTemperature());
	}

	@Scheduled(cron = "0 20 * * * ?")	//every hour at 20 minutes
	private void saveTemperatureEveryHour() {
		temperatureRepository.save(climacellClient.getCurrentTemperature());
	}

	public List<Temperature> getAllTemperatures() {
		return temperatureRepository.findAll();
	}

	public List<Temperature> getTemperaturesForEveryHour(LocalDateTime from, LocalDateTime to) {
		List<Temperature> temperatures = temperatureRepository.getTemperatures(from, to);
		return temperatures;
	}

	public List<Temperature> getAverageTemperaturesForHours(LocalDateTime from, LocalDateTime to, int hours) {
		List<Temperature> temperatures = temperatureRepository.getTemperatures(from, to);
		int counter = 0;
		int sumValues = 0;
		List<Temperature> averageTemperatures = new ArrayList<Temperature>();
		while (!from.isAfter(to)) {
			for (Temperature temperature : temperatures) {
				if (temperature.getObservationTime().isAfter(from)
						&& temperature.getObservationTime().isBefore(from.plusHours(hours))) {
					counter++;
					sumValues += temperature.getValue();
				}
			}
			if (counter != 0) {
				averageTemperatures.add(new Temperature(sumValues / counter, from.plusHours(hours/2)));
				counter = 0;
				sumValues = 0;
			}
			from = from.plusHours(hours);
		}
		return averageTemperatures;
	}

	public List<Temperature> getAverageTemperaturesForDays(LocalDateTime from, LocalDateTime to, int days) {
		return getAverageTemperaturesForHours(from, to, days*24);
	}

	
}
