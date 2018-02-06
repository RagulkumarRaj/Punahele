package com.punahele.ws.videoStream;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Base64;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

import com.punahele.utilities.json.CustomJsonBuilder;
import com.punahele.utilities.video.VideoContentManager;

@ServerEndpoint(value = "/requestVideoAsImages", encoders = {com.punahele.encoder.JsonEncoder.class})
public class VideoStreamAsImages {

	@OnMessage
	public JsonObject sendVideoAsImageArray(String strRequest){
		//Response should be a Json
		//containing 1. start position for each of the frame that is served
		//2. Should send the images as a single byte array
		//should play that range audio
		StringReader reader = new StringReader(strRequest);
		JsonObject request = Json.createReader(reader).readObject();
		int frameStartPos = Integer.parseInt(request.getString("frameStartPos")); --frameStartPos;
		int frameEndPos = Integer.parseInt(request.getString("frameEndPos")); --frameEndPos;
		VideoContentManager manager = new VideoContentManager("C:\\Videos\\OneRepublic - Feel Again");
		byte[] imageSequences = manager.returnImageSequencesAsBytes(frameStartPos, frameEndPos); //Get the set of images frames requested from client
	    int[] startPositionsFrame = manager.returnFramePositionsInImageSequence(frameStartPos, frameEndPos);
	    byte[] audioDataForTimeRange = manager.returnAudioCorresToVideoDur();

	    
	    /********************
	    for (int i = 0; i < startPositionsFrame.length; i++) {
	    	byte[] data = null;
	    	if(i == startPositionsFrame.length-1) {
	    		data = Arrays.copyOfRange(imageSequences, startPositionsFrame[i], imageSequences.length);	
	    	}
	    	else {
	    		data = Arrays.copyOfRange(imageSequences, startPositionsFrame[i], startPositionsFrame[i+1]);
	    	}
	    	try {
	    	FileOutputStream fos = new FileOutputStream("C:\\Videos\\OneRepublic - Feel Again\\Gen\\File"+i+".txt");
	    	fos.write(data); fos.close();
	    	}
	    	catch(Exception e) {}
		}
	    **/
	    CustomJsonBuilder objBuilder = new CustomJsonBuilder();
	    objBuilder.createNewObject().setJsonProperty("image_sequences", imageSequences).
	    //setJsonProperty("audio_data_time_ranges", audioDataForTimeRange).
	    setJsonProperty("start_positions_frames", startPositionsFrame);
	    return objBuilder.returnObject();
	}
	
	public static void main(String[] args) {
       new VideoStreamAsImages().sendVideoAsImageArray("{\r\n" + 
		"			\"frameStartPos\" : \"1\",\r\n" + 
		"			\"frameEndPos\" : \"3\"\r\n" + 
		"		}");
	}
}
