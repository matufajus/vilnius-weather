package com.javatask.vilniusweather.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javatask.vilniusweather.model.Temperature;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Integer>{

}
