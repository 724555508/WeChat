package com.Yang.modules.core.service.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.Yang.common.conf.CoreConf;
import com.Yang.common.utils.IoUtil;
import com.Yang.common.utils.image.OverlapImage;
import com.Yang.common.utils.nullUtil.IsNull;
import com.Yang.common.utils.oss.OSSUtil;
import com.Yang.modules.core.entity.InitConfig;
import com.Yang.modules.core.service.BasicService;
import com.Yang.modules.core.service.UserService;
import com.Yang.util.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BasicServiceImpl implements BasicService{
	
	@Resource UserService userService;

	@Override
	public String generatePosterUrl(String openId, InitConfig initConfig) {
		String param = "{\"action_name\":\"QR_LIMIT_STR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":\""
				+ openId + "\"}}}";
		//生成二维码（缺图片）
		String tickt = this.getTicket(param,initConfig.getAppid(),initConfig.getSecret());
//		String imgUrl = OSSUtil.uploadImgByOnlineUrl(
//				"https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + tickt);
		long time1 = System.currentTimeMillis();
		String path = OverlapImage.combineCodeAndPicToInputstream("https://first-love.oss-cn-beijing.aliyuncs.com/image/2019/12/31/11.jpg", "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + tickt);
		long time2 = System.currentTimeMillis();
		String imgUrl = OSSUtil.uploadImgByFile(new File(path));
		long time3 = System.currentTimeMillis();
		log.info("imgUrl:" + imgUrl);
		return imgUrl;
	}
	
	@Override
	public String getTicket(String param, String appId, String secret) {
		String url = CoreConf.GET_TIKET_URL + userService.getAccessToken(appId, secret);
		String res = com.Yang.util.HttpUtils.sendPost(url, param);
		if (IsNull.isNullOrEmpty(res)) {
			return null;
		}
		JSONObject jsonObject = (JSONObject) JSONObject.parse(res);
		String ticket = jsonObject.get("ticket").toString().replaceAll("\"", "");
		log.info("######成功获取ticket:" + ticket);
		return ticket;
	}

	@Override
	public String getMediaIdByImgUrlForEver(String imgUrl, String appId, String secret) throws IOException {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="
				+ userService.getAccessToken(appId, secret) + "&type=image";
		String result = null;

		InputStream in2 = null;
		URL realUrl = new URL(imgUrl);
		in2 = realUrl.openStream();
		File file = IoUtil.inputstreamtofile(in2);
		URL urlObj = new URL(requestUrl);

		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);

		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition:form-data;name=\"media\";filename=\"" + file.getName() + "\";filelength=\""
				+ file.length() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		OutputStream out = new DataOutputStream(con.getOutputStream());

		out.write(head);

		// 文件正文部分
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024 * 1024 * 10]; // 10M
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// 结尾
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");
		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		log.info(result);
		JSONObject parseObject = JSONObject.parseObject(result);
		return parseObject.getString("media_id");
	}

	@Override
	public Map<String, String> getOpenIdAndTokenByCode(String code, String appId, String secret) {
		Map<String, String> data = new HashMap();
		String res = HttpUtil.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret="
				+ secret + "&code=" + code + "&grant_type=authorization_code");
		log.info(res);
		JSONObject jsonObject = (JSONObject) JSONObject.parse(res);
		data.put("openid", jsonObject.get("openid").toString().replaceAll("\"", ""));
		data.put("access_token", jsonObject.get("access_token").toString().replaceAll("\"", ""));
		return data;
	}

}
