package com.javatask.vilniusweather.climacellapi;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.javatask.vilniusweather.model.Temperature;

public class TemperatureDeserializer extends StdDeserializer<Temperature>{
	
	public TemperatureDeserializer() {
        this(null);
    }

	protected TemperatureDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Temperature deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectCodec codec = parser.getCodec();
        JsonNode jsonNode = codec.readTree(parser);
		int value = jsonNode.get("temp").get("value").asInt();
		LocalDateTime observationTime = LocalDateTime.parse(jsonNode.get("observation_time").get("value").asText().substring(0, 19));
		return new Temperature(value, observationTime);
	}

}
