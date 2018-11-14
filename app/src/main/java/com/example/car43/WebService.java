package com.example.car43;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService {
	public static byte[] getImage(String path) {

		URL url;
		byte[] b = null;
		try {
			url = new URL(path); // 设置URL
			HttpURLConnection con;

			con = (HttpURLConnection) url.openConnection(); // 打开连接

			con.setRequestMethod("GET"); // 设置请求方法
			// 设置连接超时时间为2s
			con.setConnectTimeout(2000);
			InputStream in = con.getInputStream(); // 取得字节输入流

			b = StreamTool.readInputStream(in);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return b; // 返回byte数组

	}

	public static void setpt(String path) {
		try {
			URL url = new URL(path);
			HttpURLConnection con;
			con = (HttpURLConnection) url.openConnection(); // 打开连接
			InputStream in = con.getInputStream();
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
