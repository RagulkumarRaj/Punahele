package com.punahele.ws.videoStream;

import java.util.Base64;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

import com.punahele.utilities.json.CustomJsonBuilder;
import com.punahele.utilities.video.VideoContentManager;

@ServerEndpoint(value = "/requestVideoAsImages", encoders = {com.punahele.encoder.JsonEncoder.class})
public class VideoStreamAsImages {

	@OnMessage
	public JsonObject sendVideoAsImageArray(int frameStartPos, int frameEndPos){
		//Response should be a Json
		//containing 1. start position for each of the frame that is served
		//2. Should send the images as a single byte array
		//should play that range audio
		VideoContentManager manager = new VideoContentManager("SOME_FILE_NAME");
		byte[] imageSequences = manager.returnImageSequencesAsBytes(frameStartPos, frameEndPos); //Get the set of images frames requested from client
	    int[] startPositionsFrame = manager.returnFrameLenghtsBtwnRanges(frameStartPos, frameEndPos);
	    byte[] audioDataForTimeRange = manager.returnAudioCorresToVideoDur();
	    
	    CustomJsonBuilder objBuilder = new CustomJsonBuilder();
	    objBuilder.createNewObject().setJsonProperty("image_sequences", imageSequences).
	    setJsonProperty("audio_data_time_ranges", audioDataForTimeRange).
	    setJsonProperty("start_positions_frames", startPositionsFrame);
        
	    return objBuilder.returnObject();
	}
}
