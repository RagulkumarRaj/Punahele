package com.punahele.ws.videoStream;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.stream.JsonGenerator;
import javax.websocket.OnMessage;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/requestVideo", encoders = {com.punahele.encoder.JsonEncoder.class})
public class VideoStreamEndpoint {
	/*
	@OnMessage
	public byte[] echo(String message) {
		//RemoteEndpoint r = new RemoteEndpoint() {}
	    File file = new File("C:\\Videos\\OneRepublic - Feel Again.mp4");
	    byte[] data = new byte[(int) file.length()];
	    DataInputStream stream = null;
	    try {
	        stream = new DataInputStream(new FileInputStream(file));
	    } catch (FileNotFoundException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    }
	    if (stream != null) {
	        try {
	        	stream.read(data, 0, 1024 * 1024 * 10);
	            //stream.readFully(data);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        try {
	            stream.close();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	    return data;
	    }
	*/
	@OnMessage
	public JsonObject sendContent(String message){
		File file = new File("C:\\Videos\\OneRepublic - Feel Again.mp4");
	    byte[] data = new byte[(int) file.length()];
	    DataInputStream stream = null;
	    try {
	        stream = new DataInputStream(new FileInputStream(file));
	    } catch (FileNotFoundException e1) {}
	    if (stream != null) {
	        try {
	        	stream.read(data, 0, 1024 * 1024 * 36);
	            //stream.readFully(data);
	        } catch (IOException e) { e.printStackTrace(); }
	        try {
	            stream.close();
	        } catch (IOException e) {e.printStackTrace();}
	    }
	    //sun.misc.BASE64Encoder encoder= new sun.misc.BASE64Encoder();
	    //videoData.put("data",encoder.encode(data).getBytes());
	    
	    JsonObjectBuilder builder = Json.createObjectBuilder();
	    builder = Json.createObjectBuilder();
	    
	    //builder.add("Content-Range", 1024 * 1024 * 1);
	    //builder.add("Accept-Ranges", "bytes");
	    //builder.add("Content-Length", "101");
	    builder.add("Content-Range", "bytes 10485760-26214400/31457280");
	    builder.add("Content-Length", "15728640");
	    builder.add("Content-Type", "video/mp3");
	    
	    JsonObject videoHeader = builder.build();
	    
	    builder = Json.createObjectBuilder();
	    builder.add("data", Base64.getEncoder().encodeToString(data));
	    builder.add("head", videoHeader);
	    JsonObject videoData = builder.build();
	    
		return videoData;
	}
	  }