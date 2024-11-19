package ru.rsc.clicker_kombat.utils.converters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
@Converter(autoApply = true)
public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JsonNode attribute) {
        if (attribute == null)
            return null;
        return attribute.asText();
    }

    @Override
    @SneakyThrows
    public JsonNode convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        return objectMapper.readTree(dbData);
    }
}
