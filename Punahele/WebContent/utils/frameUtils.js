function extractImageData(framePos, frameData, counter) {
		 if(counter == framePos.length - 1){
         	var frame = b64ToBlob(frameData.slice(framePos[counter], frameData.length), "image/jpeg");
         }
         else{
         	var frame = b64ToBlob(frameData.slice(framePos[counter], framePos[counter + 1]), "image/jpeg");
         }
		var image = document.getElementById('frame');
		image.src = window.URL.createObjectURL(frame);
		console.log("Url is : "+image.src + "at time: "+new Date().getTime() / 1000 + " :counter "+counter);
		
}


function createObjectURL(file) {
	if (window.webkitURL) {
		return window.webkitURL.createObjectURL(file);
	} else if (window.URL && window.URL.createObjectURL) {
		return window.URL.createObjectURL(file);
	} else {
		return null;
	}
}