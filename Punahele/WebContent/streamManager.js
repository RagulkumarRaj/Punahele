function readFrameData(evt){
	var imageData = JSON.parse(evt["data"]);
	//make sure to decode imageSeqs and framePos
	var frameData = b64ToArray(imageData["image_sequences"]);
	var framePos = (imageData["start_positions_frames"]).split(",");
	
	console.log(new Date().getTime() / 1000);
	for (var i = 0; i < framePos.length - 1; i++) {
		(function(index) {
		    //setTimeout(extractImageData(framePos, frameData, index), 300);
		    //window.setTimeout(extractImageData, 3000, framePos, frameData, index);
			setTimeout(extractImageData, i*300, framePos, frameData, index);
		 })(i);
	}
}

function requestFrames(start, end){
	var message = JSON.stringify({
		"frameStartPos" : ""+start+"",
		"frameEndPos" : ""+end+""
	});
	websocket.send(message);	
}
