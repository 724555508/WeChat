package com.Yang.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.Yang.util.RandomCharUtil;

public class IoUtil {
	
	static String osName = System.getProperties().getProperty("os.name");

	public static File inputstreamtofile(InputStream ins) throws IOException {
//		File file = new File("F://");
		File tmp = File.createTempFile(RandomCharUtil.getRandomALLChar(6), ".jpg", osName.indexOf("Windows") != -1 ? new File("Y:\\") : new File("/MediaId/data/"));
		OutputStream os = new FileOutputStream(tmp);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		ins.close();
		return tmp;
	}

}
