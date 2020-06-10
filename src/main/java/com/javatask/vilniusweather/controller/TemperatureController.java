package com.javatask.vilniusweather.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javatask.vilniusweather.model.Temperature;
import com.javatask.vilniusweather.service.TemperatureService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TemperatureController {

	@Autowired
	TemperatureService temperatureService;

	@GetMapping("/")
	public String showTemperatureHistory() {
		return "temperature-history";
	}

	@GetMapping("/temperature/last/day")
	public @ResponseBody List<Temperature> getTemperatureOfLastDay() {
		LocalDateTime from = LocalDateTime.now().minusDays(1);
		LocalDateTime to = LocalDateTime.now();
		List<Temperature> temperatures = temperatureService.getTemperaturesForEveryHour(from, to);
		return temperatures;
	}

	@GetMapping("/temperature/last/week")
	public @ResponseBody List<Temperature> getTemperatureOfLastWeek() {
		LocalDateTime from = LocalDateTime.now().minusWeeks(1);
		LocalDateTime to = LocalDateTime.now();
		List<Temperature> temperatures = temperatureService.getAverageTemperaturesForHours(from, to, 8);
		return temperatures;
	}

	@GetMapping("/temperature/last/month")
	public @ResponseBody List<Temperature> getTemperatureOfLastMonth() {
		LocalDateTime from = LocalDateTime.now().minusMonths(1);
		LocalDateTime to = LocalDateTime.now();
		List<Temperature> temperatures = temperatureService.getAverageTemperaturesForHours(from, to, 24);
		return temperatures;
	}

}
