package com.punahele.utilities.json;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import sun.misc.BASE64Encoder;

public class CustomJsonBuilder {
	private JsonObjectBuilder builder = null;

	public CustomJsonBuilder setJsonProperty(String propName, String propValue) {
		builder.add(propName, propValue);
		return this;
	}

	public CustomJsonBuilder setJsonProperty(String propName, byte[] propValue) {
		BASE64Encoder encoder = new BASE64Encoder();
		String base64Val = encoder.encode(propValue);
		builder.add(propName, base64Val);
		return this;
	}

	public CustomJsonBuilder setJsonProperty(String propName, int[] propValue) {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < propValue.length - 1; i++) {
			sb.append(propValue[i] + ",");
		}
		sb.append(propValue[propValue.length - 1]);
		builder.add(propName, sb.toString());
		return this;
	}
	
	public CustomJsonBuilder setJsonProperty(String propName, JsonObject propValue) {
		builder.add(propName, propValue);
		return this;
	}

	public CustomJsonBuilder createNewObject() {
		builder = null;
		builder = Json.createObjectBuilder();
		return this;
	}

	public JsonObject returnObject() {
		return builder.build();
	}
	
	public static void main(String[] args) {
		CustomJsonBuilder builder = new CustomJsonBuilder();
		JsonObject obj = builder.createNewObject().setJsonProperty("test", new byte[] {0,1,6,0,1}).returnObject();;
		System.out.println(obj);
		
		JsonObject obj1 = builder.createNewObject().setJsonProperty("test1", "test1").setJsonProperty("test", obj).returnObject();;
		System.out.println(obj1);
		
		JsonObject obj2 = builder.createNewObject().setJsonProperty("test2", new int[] {1,2,3}).setJsonProperty("test1", obj1).returnObject();;
		System.out.println(obj2);
	}
}
