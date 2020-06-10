package com.javatask.vilniusweather.climacellapi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatask.vilniusweather.model.Temperature;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClimacellClient {

	@Value("${api.url.realtime}")
	private String urlRealTime;

	@Value("${api.url.historical}")
	private String urlHistorical;

	@Value("${api.city.lat}")
	private String lat;

	@Value("${api.city.lon}")
	private String lon;

	@Value("${api.key}")
	private String key;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	public Temperature getCurrentTemperature() {
		log.info("Making request to: " + urlRealTime);
		HttpHeaders headers = new HttpHeaders();
		headers.set("apikey", key);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlRealTime).queryParam("lat", lat)
				.queryParam("lon", lon).queryParam("fields", "temp");
		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				String.class);
		log.debug("Response: " + response.getBody());
		try {
			return convertJsonToTemperature(response.getBody());
		} catch (JsonProcessingException e) {
			log.error("There was an error converting json to Temperature object: " + e);
			return null;
		}
	}

	public List<Temperature> getHistoricalTemperatureForDate(LocalDate date) {
		log.info("Making request to: " + urlHistorical);
		HttpHeaders headers = new HttpHeaders();
		headers.set("apikey", key);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlHistorical).queryParam("lat", lat)
				.queryParam("lon", lon).queryParam("start_time", date.toString())
				.queryParam("end_time", date.plusDays(1).toString()).queryParam("fields", "temp");
		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				String.class);
		log.debug("Response: " + response.getBody());
		try {
			List<Temperature> temperatures = convertJsonToTemperatureList(response.getBody());
			// return only one temperature for each hour at minute - 20
			return temperatures.stream().filter(t -> t.getObservationTime().getMinute() == 20)
					.collect(Collectors.toList());
		} catch (JsonProcessingException e) {
			log.error("There was an error converting json to List of Temperatures: " + e);
			return null;
		}
	}

	public List<Temperature> getLastMonthTemperature() {
		List<Temperature> temperaturesOfLastMonth = new ArrayList<Temperature>();
		List<LocalDate> datesOfMonth = new ArrayList<LocalDate>();
		LocalDate from = LocalDate.now().minusMonths(1);
		LocalDate to = LocalDate.now();
		
		while (!from.isAfter(to)) {
			datesOfMonth.add(from);
			from = from.plusDays(1);
		}

		for (LocalDate date : datesOfMonth) {
			List<Temperature> temperaturesOfDay = getHistoricalTemperatureForDate(date);
			temperaturesOfLastMonth.addAll(temperaturesOfDay);
		}

		return temperaturesOfLastMonth;
	}

	public Temperature convertJsonToTemperature(String json) throws JsonMappingException, JsonProcessingException {
		return objectMapper.readValue(json, Temperature.class);
	}

	public List<Temperature> convertJsonToTemperatureList(String json)
			throws JsonMappingException, JsonProcessingException {
		return objectMapper.readValue(json, new TypeReference<List<Temperature>>() {
		});
	}

}
