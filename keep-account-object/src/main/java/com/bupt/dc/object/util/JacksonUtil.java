package com.bupt.dc.object.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class JacksonUtil {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        //objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        //objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        //objectMapper.configure(JsonParser.Feature.INTERN_FIELD_NAMES, true);
        //objectMapper.configure(JsonParser.Feature.CANONICALIZE_FIELD_NAMES, true);
        //objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        objectMapper.registerModule(javaTimeModule);
    }

    public static String encode(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            log.error("encode(Object)", e);
        } catch (JsonMappingException e) {
            log.error("encode(Object)", e);
        } catch (IOException e) {
            log.error("encode(Object)", e);
        }
        return null;
    }

    /**
     * 将json string反序列化成对象
     *
     * @param json
     * @param valueType
     * @return
     */
    public static <T> T decode(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonParseException e) {
            log.error("decode(String, Class<T>)", e);
        } catch (JsonMappingException e) {
            log.error("decode(String, Class<T>)", e);
        } catch (IOException e) {
            log.error("decode(String, Class<T>)", e);
        }
        return null;
    }
}
