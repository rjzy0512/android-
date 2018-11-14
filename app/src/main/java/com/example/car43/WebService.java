package com.example.car43;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService {
	public static byte[] getImage(String path) {

		URL url;
		byte[] b = null;
		try {
			url = new URL(path); // ����URL
			HttpURLConnection con;

			con = (HttpURLConnection) url.openConnection(); // ������

			con.setRequestMethod("GET"); // �������󷽷�
			// �������ӳ�ʱʱ��Ϊ2s
			con.setConnectTimeout(2000);
			InputStream in = con.getInputStream(); // ȡ���ֽ�������

			b = StreamTool.readInputStream(in);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return b; // ����byte����

	}

	public static void setpt(String path) {
		try {
			URL url = new URL(path);
			HttpURLConnection con;
			con = (HttpURLConnection) url.openConnection(); // ������
			InputStream in = con.getInputStream();
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
