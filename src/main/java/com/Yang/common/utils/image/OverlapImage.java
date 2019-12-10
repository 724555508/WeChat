package com.Yang.common.utils.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import com.Yang.util.RandomCharUtil;

public class OverlapImage {

	static String osName = System.getProperties().getProperty("os.name");
	
	/* 
	 * combineCodeAndPicToInputstream
	 * @description：合成二维码和图片为输出流，可用于下载或直接展示
	 * @author 李阳
	 * @date 2018/12/13
	 * @params [backPicPath, code]
	 * @return java.io.InputStream
	 */
	public static final void combineCodeAndPicToFile(String bigPicPath, String smallPicPath) {
	    try {
	        BufferedImage big = ImageIO.read(new File(bigPicPath));
	         BufferedImage small = ImageIO.read(new File(smallPicPath));
//	        BufferedImage small = code;
	        Graphics2D g = big.createGraphics();

	        int width = 220;
	        int height = 220;
	        //二维码或小图在大图的左上角坐标
	        int x = (big.getWidth() - width) - 120;
	        int y = (big.getHeight() - height) - 30;   //二维码距大图下边距100
	        g.drawImage(small, x, y, width , height, null);
	        g.dispose();
	        //为了保证大图背景不变色，formatName必须为"png"
	        ImageIO.write(big, "png", new File("C:\\Users\\Administrator\\Desktop\\33.jpg"));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static final String combineCodeAndPicToInputstream(String bigPicUrl, String smallPicUrl) {
	    try {
	        BufferedImage big = ImageIO.read(inputStreamForUrl(bigPicUrl));
	         BufferedImage small = ImageIO.read(inputStreamForUrl(smallPicUrl));
	        Graphics2D g = big.createGraphics();

	        int width = 220;
	        int height = 220;
	        //二维码或小图在大图的左上角坐标
	        int x = (big.getWidth() - width) - 120;
	        int y = (big.getHeight() - height) - 30;   //二维码距大图下边距100
	        g.drawImage(small, x, y, width , height, null);
	        g.dispose();
	        //为了保证大图背景不变色，formatName必须为"png"
	        String path = osName.indexOf("Windows") != -1 ? "C:\\Users\\Administrator\\Desktop\\" : "/MediaId/data/";
	        path = path + RandomCharUtil.getRandomALLChar(6) + ".jpg";
	        ImageIO.write(big, "jpg", new File(path));
	        return path;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("二维码合成失败");
	    }
	}
	
	
	public static void main(String[] args) {
		combineCodeAndPicToInputstream("http://192.168.100.12/image/11.jpg","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQFT8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAybDRERVlpR1BkU2wxMDAwMHcwN2gAAgS4LLldAwQAAAAA");
		
	}
	
	public static InputStream inputStreamForUrl(String url) {
		
		URLConnection connection;
		try {
			connection = new URL(url).openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			connection.connect();

			return connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
