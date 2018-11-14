package com.example.car43;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {
	public static byte[] readInputStream(InputStream in) throws Exception {
		int len = 0;
		byte buf[] = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len); // ������д���ڴ�
		}
		out.close(); // �ر��ڴ������
		return out.toByteArray(); // ���ڴ������ת����byte����
	}

}
