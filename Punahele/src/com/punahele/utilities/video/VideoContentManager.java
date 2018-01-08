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

	public int[] returnFrameLenghtsBtwnRanges(int startFramePosition, int endFramePosition) {
		File positionsFile = new File(foldeName + "\\Positions.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(positionsFile));
			String line = br.readLine();
			String positionsStr[] = line.trim().split(" ");
			int[] positions = new int[positionsStr.length];
			for (int i = 0; i < positionsStr.length; i++) {
				positions[i] = Integer.parseInt(positionsStr[i]);
			}
			br.close();
			return positions;
		} catch (IOException io) {
			return new int[0];
		}

	}

	public byte[] returnAudioCorresToVideoDur() {
		return null;
	}

	public void loadPositionsAsMap() throws IOException {
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
