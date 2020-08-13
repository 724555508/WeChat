package com.Yang.common.utils.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;


import com.Yang.common.conf.CoreConf;
import com.Yang.common.utils.nullUtil.IsNull;
import com.Yang.util.DateUtil;
import com.Yang.util.RandomCharUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OSSUtil {
	
	private static String ENDPOINT = "";
	private static String ACCESS_KEY_ID = "";
	private static String ACCESS_KEY_SECRET = "";

	public static final OSSClient getOSSClient() {
		return new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
	}

	public static final boolean createBucket(OSSClient client, String bucketName) {
		Bucket bucket = client.createBucket(bucketName);
		return bucketName.equals(bucket.getName());
	}

	public static final void deleteBucket(OSSClient client, String bucketName) {
		client.deleteBucket(bucketName);
	}

	public static final String uploadObject2OSS(OSSClient client, File file, String bucketName, String diskName) {
		String resultStr = null;
		try {
			InputStream is = new FileInputStream(file);
			String fileName = file.getName();
			Long fileSize = Long.valueOf(file.length());

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(is.available());
			metadata.setCacheControl("no-cache");
			metadata.setHeader("Pragma", "no-cache");
			metadata.setContentEncoding("utf-8");
			metadata.setContentType(getContentType(fileName));
			metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");

			PutObjectResult putResult = client.putObject(bucketName, diskName, is, metadata);

			resultStr = putResult.getETag();
		} catch (Exception localException) {
		}
		return resultStr;
	}

	public static final String uploadOSSFormInputString(OSSClient client, InputStream inputStream, String bucketName,
			String key, String contentType) {
		String resultStr = null;
		try {
			ObjectMetadata meta = new ObjectMetadata();
			if (contentType != null) {
				meta.setContentType(contentType);
			}
			PutObjectResult putResult = client.putObject(bucketName, key, inputStream, meta);
			resultStr = putResult.getETag();
		} catch (Exception localException) {
		}
		return resultStr;
	}

	public static final InputStream getOSS2InputStream(OSSClient client, String bucketName, String diskName,
			String key) {
		OSSObject ossObj = client.getObject(bucketName, diskName + key);
		return ossObj.getObjectContent();
	}

	public static void deleteFile(OSSClient client, String bucketName, String diskName, String key) {
		client.deleteObject(bucketName, diskName + key);
	}

	public static final String getContentType(String fileName) {
		String fileExtension = fileName.substring(fileName.lastIndexOf("."));
		if (".bmp".equalsIgnoreCase(fileExtension)) {
			return "image/bmp";
		}
		if (".gif".equalsIgnoreCase(fileExtension)) {
			return "image/gif";
		}
		if ((".jpeg".equalsIgnoreCase(fileExtension)) || (".jpg".equalsIgnoreCase(fileExtension))
				|| (".png".equalsIgnoreCase(fileExtension))) {
			return "image/jpeg";
		}
		if (".html".equalsIgnoreCase(fileExtension)) {
			return "text/html";
		}
		if (".txt".equalsIgnoreCase(fileExtension)) {
			return "text/plain";
		}
		if (".vsd".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.visio";
		}
		if ((".ppt".equalsIgnoreCase(fileExtension)) || (".pptx".equalsIgnoreCase(fileExtension))) {
			return "application/vnd.ms-powerpoint";
		}
		if ((".doc".equalsIgnoreCase(fileExtension)) || (".docx".equalsIgnoreCase(fileExtension))) {
			return "application/msword";
		}
		if (".xml".equalsIgnoreCase(fileExtension)) {
			return "text/xml";
		}
		return "text/html";
	}

	public static void main(String[] args) {
//		OSSUtil ossunit = new OSSUtil();
		OSSClient client = getOSSClient();

		String flilePathName = "C:/Users/Administrator/Desktop/10.jpg";
		File file = new File(flilePathName);
		String diskName = "image/2018/0228/";
		String md5key = uploadObject2OSS(client, file, "first-love", diskName);
		System.out.println("上传后的文件MD5数字唯一签名:" + md5key);
	}

	public static String uploadImgByOnlineUrl(String url) {
		if (IsNull.isNullOrEmpty(url)) {
			return "";
		}
		OSSClient ossClient = getOSSClient();
		InputStream in = null;
		try {
			URLConnection connection = new URL(url).openConnection();

			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			connection.connect();

			in = connection.getInputStream();
			String key = prefix();
			
			uploadOSSFormInputString(ossClient, in, "first-love", key, "image/jpeg");
			log.info("图片上传成功:" + CoreConf.OSS_IMAGE_PREFIX + key);
			return CoreConf.OSS_IMAGE_PREFIX + key;
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			ossClient.shutdown();
		}
		return "";
	}
	
	public static String uploadImgByFile(File file) {
		OSSClient client = getOSSClient();
		String key = prefix();
		uploadObject2OSS(client, file, "first-love", key);
		log.info("图片上传成功:" + CoreConf.OSS_IMAGE_PREFIX + key);
		return CoreConf.OSS_IMAGE_PREFIX + key;
	}
	
	
	public static String prefix() {
		return "image/" + 
				DateUtil.formatToString(new Date(), new String[] { "YYYY" }) + "/" + 
				DateUtil.formatToString(new Date(), new String[] { "MM" }) + "/" + 
				DateUtil.formatToString(new Date(), new String[] { "dd" }) + "/" +
				"i"+RandomCharUtil.getRandomALLChar(7) + ".jpg";
	}
}
