package com.punahele.utilities.video;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class VideoContentManager {
	String foldeName;
	private Map<Integer, String> positionsMap;

	public VideoContentManager(String foldeName) {
		this.foldeName = foldeName;
		positionsMap = new HashMap<>();
		try {
			loadPositionsAsMap();
		} catch (IOException e) {
		}
	}

	/**
	 * With the start position and end position of the video, the method returns the portion 
	 * of the full video between the given positions
	 * @param startPositionFrame
	 * @param endPositionFrame
	 * @return
	 */
	public byte[] returnImageSequencesAsBytes(int startPositionFrame, int endPositionFrame) {
		try {
			String startFrameData = positionsMap.get(startPositionFrame);
			String temp[] = startFrameData.split(",");
			int startPos = Integer.parseInt(temp[0]);

			String endFrameData = positionsMap.get(endPositionFrame);
			temp = endFrameData.split(",");
			int endPos = Integer.parseInt(temp[0]) + Integer.parseInt(temp[1]);

			byte[] data = new byte[endPos - startPos];

			RandomAccessFile f = new RandomAccessFile(foldeName + "\\BigFrame.jpeg", "rw");
			f.seek(startPos);
			for (int cursor = startPos; cursor <= endPos; cursor++) {
				data[cursor] = f.readByte();
			}
			f.close();
			return data;
		} catch (IOException e) {
			return new byte[0];
		}
	}

	/**
	 * When a portion of the video is returned to the user as a sequence of images, this method
	 * returns the positions of the image frames within that portion of data being returned
	 * @param startFramePosition
	 * @param endFramePosition
	 * @return
	 */
	public int[] returnFramePositionsInImageSequence(int startFramePosition, int endFramePosition) {
        String temp[] =  positionsMap.get(startFramePosition).split(",");
        int subVal = Integer.parseInt(temp[0]); int[] framesPos = 
        		new int[endFramePosition-startFramePosition];
		for(int cur=startFramePosition; cur<=endFramePosition; cur++) {
			String tempParts[] = positionsMap.get(cur).split(",");
			framesPos[cur] = Integer.parseInt(tempParts[0]) - subVal;
       }
		return framesPos;
	}

	public byte[] returnAudioCorresToVideoDur() {
		return null;
	}
  // map will have key as frame number and the value will be comma separated value containing
  // 1. the frame start position and 2. The length of frame
	private void loadPositionsAsMap() throws IOException {
		File positionsFile = new File(foldeName + "\\Positions.txt");
		BufferedReader br = new BufferedReader(new FileReader(positionsFile));
		String line = br.readLine();
		while (null != line) {
			String parts[] = line.split("-");
			positionsMap.put(Integer.parseInt(parts[0]), parts[1]);
			line = br.readLine();
		}
		br.close();
	}
}
