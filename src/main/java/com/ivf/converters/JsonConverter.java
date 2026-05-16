package com.ivf.converters;

import java.util.Map;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tools.jackson.databind.ObjectMapper;

@Converter
public class JsonConverter implements AttributeConverter<Map<String, Object>, String> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Map<String, Object> attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Map.class);
        } catch (Exception e) {
            return new java.util.HashMap<>();
        }
    }
}
