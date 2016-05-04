package com.project.framework.json;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonStringSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator gen,SerializerProvider sp) throws IOException,JsonProcessingException {
		   System.out.println(value);
	       gen.writeString(value);
	}
}
