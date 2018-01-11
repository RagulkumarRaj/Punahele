package com.punahele.ws.videoStream;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

public class TestJsonStuffs {

	public static void main(String[] args) throws IOException {
		JsonObject obj = Json.createObjectBuilder().add("test", 1555).build();
		System.out.println(obj.getInt("test"));
	}
}
