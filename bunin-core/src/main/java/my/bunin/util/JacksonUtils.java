package my.bunin.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JacksonUtils {

    public static final String ISO_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = objectMapper();

    private static ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModules(new Jdk8Module(), new GuavaModule(),
                        new JavaTimeModule(), new JodaModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String writeValueAsString(Object value) {
        try {
            return DEFAULT_OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert the object {} to json string!", value);
            throw new IllegalArgumentException(String.format(
                    "Failed to convert the object %s to json string!", value), e);
        }
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return DEFAULT_OBJECT_MAPPER.readValue(content, valueType);
        } catch (IOException e) {
            log.error("Failed to parse json text {} to object!", content);
            throw new IllegalArgumentException(String.format(
                    "Failed to parse json text %s to object!", content), e);
        }
    }

}
