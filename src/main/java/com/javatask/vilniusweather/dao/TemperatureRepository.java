package com.javatask.vilniusweather.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javatask.vilniusweather.model.Temperature;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Integer>{
	
	@Query("select t from Temperature t where t.observationTime >= ?1 AND t.observationTime <= ?2")
	List<Temperature> getTemperatures(LocalDateTime from, LocalDateTime to);

}
