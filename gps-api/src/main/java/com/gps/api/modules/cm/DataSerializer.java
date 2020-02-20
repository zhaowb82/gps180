package com.gps.api.modules.cm;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class DataSerializer<T> extends JsonDeserializer<T> {
    @Override
    public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonToken currentToken = jp.getCurrentToken();
        if (currentToken == JsonToken.START_ARRAY) {
            return ctxt.readValue(jp, Object.class) == null ? null : null;
        } else if (currentToken == JsonToken.START_OBJECT) {
            String d = ctxt.readValue(jp, String.class);
            return (T) ctxt.readValue(jp, String.class);
        }
        return null;
    }
}
