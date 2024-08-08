package ru.rsc.clicker_kombat.utils.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
@Converter(autoApply = true)
public class JsonBConverter<T> implements AttributeConverter<T,String> {
   private final Class<T> clazz;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(T attribute) {
        if(attribute == null)
            return null;
        return objectMapper.writeValueAsString(attribute) ;
    }

    @SneakyThrows
    @Override
    public T convertToEntityAttribute(String dbData) {
        if(dbData == null)
            return null;
        return objectMapper.readValue(dbData,clazz);
    }
}