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
import javax.json.JsonReader;

public class TestJsonStuffs {

	public static void main(String[] args) throws IOException {
		File file = new File("C:\\Videos\\OneRepublic - Feel Again.mp4");
	    byte[] data = new byte[(int) file.length()];
	    DataInputStream stream = null;
	    stream = new DataInputStream(new FileInputStream(file));
	    
	    if (stream != null) {
	        stream.read(data, 0, 1024 * 2);
	    }
	    System.out.println(data.length);
        
	    JsonObject reader = Json.createReader(stream).readObject();
        JsonObject jso = Json.createArrayBuilder().add(1).build();
	    stream.close();
	}
}
