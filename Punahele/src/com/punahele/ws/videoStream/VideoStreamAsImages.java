package com.punahele.ws.videoStream;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/requestVideoAsImages", encoders = {com.punahele.encoder.JsonEncoder.class})
public class VideoStreamAsImages {

	@OnMessage
	public void sendVideoAsImageArray(String message){
		//Response should be a Json
		//containing 1. start position for each of the frame that is served
		//
	}
}
