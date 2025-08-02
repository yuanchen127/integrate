package org.ike.integrate.config.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class UppercaseDeserializer extends JsonDeserializer<String> {
	@Override
	public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
		String value = p.getText();
		return value != null ? value.toUpperCase() : null;
	}
}
