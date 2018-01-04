package com.punahele.encoder;

import java.io.IOException;
import java.io.Writer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class JsonEncoder implements Encoder.TextStream<JsonObject> {

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

	@Override
	public void encode(JsonObject obj, Writer writer) throws EncodeException, IOException {
		try (JsonWriter jsonWriter = Json.createWriter(writer)) {
			jsonWriter.writeObject(obj);
		}
	}
}
