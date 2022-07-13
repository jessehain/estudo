package com.magalu.desafiotecnico.common;

import static java.time.format.DateTimeFormatter.ofPattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter {

  protected static final DateTimeFormatter FORMATTER = ofPattern("yyyy-MM-dd");

  public static String toJson(Object object) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(FORMATTER));
    javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(FORMATTER));
    mapper.registerModule(javaTimeModule);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    return mapper.writeValueAsString(object);
  }

}
