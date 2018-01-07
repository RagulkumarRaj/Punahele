package com.punahele.utilities.video;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.api.awt.AWTFrameGrab;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.common.tools.MainUtils;
import org.jcodec.containers.mp4.MP4Util;

import com.google.common.io.Closer;

public class VideoToFramesConverter {
	public void convertVideoToFrame(String fileName) throws IOException, JCodecException {
		// Get some logic to convert videos to frames.
		// Once that is done, write all the frames to a single file
		// Create another file that will contain the frame number and its start position
		// Try JCodec to convert videos to frames
		FileChannelWrapper in = null;
		try {
			in = NIOUtils.readableChannel(new File(fileName));
			AWTFrameGrab fg = AWTFrameGrab.createAWTFrameGrab(in);
			String parts[] = fileName.split("\\.");
			new File(parts[0]).mkdir();
			for (int i = 0; i < 4700; i++) {
				BufferedImage frame = fg.getFrame();
				File file = new File(parts[0]+"\\Frame" + i + ".jpeg");
				file.createNewFile();
				ImageIO.write(frame, "jpeg", file);
			}
			mergeImagesToFile(parts[0], parts[0]+"\\BigFinal.jpeg");
		} finally {
			NIOUtils.closeQuietly(in);
		}
	}

	public void mergeImagesToFile(String folderName, String finalFilename) throws IOException {
		StringBuilder positions = new StringBuilder();
		File videoFolder = new File(folderName);
		File[] frames = videoFolder.listFiles();
		final Closer closer = Closer.create();

		final RandomAccessFile outFile;
		final FileChannel outChannel;

		try {
		    outFile = closer.register(new RandomAccessFile(finalFilename, "rw"));
		    outChannel = closer.register(outFile.getChannel());
		    for (final File frame: frames) {
		        doWrite(outChannel, frame);
		        positions.append(frame.length()+" ");
		    }
		} finally {
			BufferedWriter writer = new BufferedWriter(new FileWriter(folderName+"\\Positions.txt"));
		    writer.write(positions.toString());
		    writer.close();
			closer.close();
		}
	}
	
	private static void doWrite(final WritableByteChannel channel, final File file)
		    throws IOException
		{
		    final Closer closer = Closer.create();

		    final RandomAccessFile inFile;
		    final FileChannel inChannel;

		    try {
		        inFile = closer.register(new RandomAccessFile(file, "r"));
		        inChannel = closer.register(inFile.getChannel());
		        inChannel.transferTo(0, inChannel.size(), channel);
		    } finally {
		        closer.close();
		    }
		}
	
	public static void main(String[] args) throws IOException, JCodecException {
		new VideoToFramesConverter().convertVideoToFrame("C:\\Videos\\OneRepublic - Feel Again.mp4");
	}
}
