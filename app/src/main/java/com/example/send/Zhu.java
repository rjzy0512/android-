package com.example.send;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Zhu {
	private int port = 60000;
	private DataInputStream bInputStream;
	private DataOutputStream bOutputStream;
	private Socket socket;
	private byte[] rbyte = new byte[512];
	private Timer timer;
	public short CHECKSUM=0x00;
	public byte MAJOR = 0x00;
	public byte FIRST = 0x00;
	public byte SECOND = 0x00;
	public byte THRID = 0x00;
	public int sp_n = 20, en_n = 125;
	private byte[] data = { 0x00, 0x00, 0x00, 0x00, 0x00 };
	public static byte[] openbyte = new byte[6];
	public static byte[] closesbyte = new byte[6];

	private Thread reThread = new Thread(new Runnable() {
		@Override
		public void run() {
			// TODO Auto1-generated method stub
			while (socket != null && !socket.isClosed()) {
				try {
					bInputStream.read(rbyte);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	});
	

	public void connect(final Handler handler, String IP) {

		try {
			socket = new Socket(IP, port);
			bInputStream = new DataInputStream(socket.getInputStream());
			bOutputStream = new DataOutputStream(socket.getOutputStream());
			reThread.start();
			Message message = new Message();
			message.obj = rbyte;
			timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					Message message = new Message();
					message.obj = rbyte;
					message.what = 1;
					handler.sendMessage(message);
				}
			}, 0, 500);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send() {
		try {
			CHECKSUM=(short) ((MAJOR+FIRST+SECOND+THRID)%256);
			// 发送数据字节数组
			byte[] sbyte = { 0x55, (byte) 0X0AA, MAJOR, FIRST, SECOND, THRID,(byte) CHECKSUM,(byte) 0xBB };
			bOutputStream.write(sbyte, 0, sbyte.length);
			bOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send1() {
		try {
			CHECKSUM=(short) ((MAJOR+FIRST+SECOND+THRID)%256);
			// 发送数据字节数组
			byte[] sbyte = { 0x55, (byte) 0X0a, MAJOR, FIRST, SECOND, THRID,(byte) CHECKSUM,(byte) 0xBB };
			bOutputStream.write(sbyte, 0, sbyte.length);
			bOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//传输
	public void chan()
	{
		MAJOR = (byte)0x80;
		FIRST = 0x01;
		SECOND = 0x00;
		THRID = 0x00;
		send();
	}
	
	public void guan() {
		MAJOR = (byte) 0x80;
		FIRST = 0x00;
		SECOND = 0x00;
		THRID = 0x00;
		send();
	}

	// 停车
	public void stop() {
		MAJOR = 0x01;
		FIRST = 0x00;
		SECOND = 0x00;
		THRID = 0x00;
		send();
	}

	// 红外
	public void infrared(byte one, byte two, byte thrid, byte four, byte five,
			byte six) {
		MAJOR = 0x10;
		FIRST = one;
		SECOND = two;
		THRID = thrid;
		send();
		yanchi(1000);
		MAJOR = 0x11;
		FIRST = four;
		SECOND = five;
		THRID = six;
		send();
		yanchi(1000);
		MAJOR = 0x12;
		FIRST = 0x00;
		SECOND = 0x00;
		THRID = 0x00;
		send();
		yanchi(2000);
	}



	public void alert() {// 打开报警器
		infrared((byte) 0x03, (byte) 0x05, (byte) 0x14, (byte) 0x45,
				(byte) 0xDE, (byte) 0x92);
	}

	public void fan() {// 打开风扇
		infrared((byte) 0x67, (byte) 0x34, (byte) 0x78, (byte) 0xA2,
				(byte) 0xFD, (byte) 0x27);
	}

	public void stereo(int a, int b) {// 立体显示
		for (int j = 0; j < 3; j++) {
			switch (a) {
			case 1:// 颜色
				data[0] = 0x13;
				data[1] = (byte) b;
				infrared_stereo(data);
				break;
			case 2:// 形状
				data[0] = 0x12;
				data[1] = (byte) b;
				infrared_stereo(data);
				break;
			case 3:// 路况
				data[0] = 0x14;
				data[1] = (byte) b;
				infrared_stereo(data);
				break;
			case 4:// 默认信息
				data[0] = 0x15;
				data[1] = 0x01;
				infrared_stereo(data);
				break;
			case 6:// 距离
				data[0] = 0x11;
				data[1] = (byte) (0x30+b/10);
				data[2] = (byte) (0x30+b%10);
				infrared_stereo(data);
				break;
			}
			yanchi(3000);
		}
	}
	
	public void chepai(String a){
		int j;
		char li[] = new char[8]; 
		for(j=0;j<a.length();j++){
			li[j] = a.charAt(j);
		}
		data[0] = 0x20;
		data[1] = (byte) (li[0]);
		data[2] = (byte) (li[1]);
		data[3] = (byte) (li[2]);
		data[4] = (byte) (li[3]);
		infrared_stereo(data);
		data[0] = 0x10;
		data[1] = (byte) (li[4]);
		data[2] = (byte) (li[5]);
		data[3] = (byte) (li[6]);
		data[4] = (byte) (li[7]);
		infrared_stereo(data);
	}
	
	public void picture(int i) {// 图片上翻和下翻
		if (i == 1)
			MAJOR = 0x50;
		else
			MAJOR = 0x51;
		FIRST = 0x00;
		SECOND = 0x00;
		THRID = 0x00;
		send();
	}

	public  void cixuanfu(){
		MAJOR = 0x01;
		FIRST = 0x01;
		SECOND = 0x00;
		THRID = 0x00;
		send1();
	}

	public void gear(int i) {// 光照档位加
		if (i == 1)
			MAJOR = 0x61;
		else if (i == 2)
			MAJOR = 0x62;
		else if (i == 3)
			MAJOR = 0x63;
		else if (i == 4)
			return;
		FIRST = 0x00;
		SECOND = 0x00;
		THRID = 0x00;
		send();
	}
		public void qian() {
			MAJOR = 0x02;
			FIRST = (byte) (sp_n & 0xFF);
			SECOND = (byte) (25 & 0xff);
			THRID = (byte) (25 >> 8);
			send();
		}
		public void qian2() {
			MAJOR = 0x02;
			FIRST = (byte) (sp_n & 0xFF);
			SECOND = (byte) (80 & 0xff);
			THRID = (byte) (60 >> 8);
			send();
		}
		public void qian3() {
			MAJOR = 0x02;
			FIRST = (byte) (sp_n & 0xFF);
			SECOND = (byte) (200 & 0xff);
			THRID = (byte) (200 >> 8);
			send();
		}
		public void qian4() {
			MAJOR = 0x02;
			FIRST = (byte) (sp_n & 0xFF);
			SECOND = (byte) (140 & 0xff);
			THRID = (byte) (140 >> 8);
			send();
		}
		public void hou2() {	
			int en_n1 = 225;
			MAJOR = 0x03;
			FIRST = (byte) (sp_n & 0xFF);
			SECOND = (byte) (en_n1 & 0xff);
			THRID = (byte) (en_n1 >> 8);
			send();
		}
		public void hou() {	
			int en_n1 = 40;
			MAJOR = 0x03;
			FIRST = (byte) (sp_n & 0xFF);
			SECOND = (byte) (en_n1 & 0xff);
			THRID = (byte) (en_n1 >> 8);
			send();
		}
		public void hou3() {	
			int en_n1 = 50;
			MAJOR = 0x03;
			FIRST = (byte) (sp_n & 0xFF);
			SECOND = (byte) (en_n1 & 0xff);
			THRID = (byte) (en_n1 >> 8);
			send();
		}
	
		public void zuo() {
			MAJOR = 0x04;
			FIRST = (byte) (140 & 0xFF);
			SECOND = 0x00;
			THRID = 0x00;
			send();
		}

		public void you() {
			MAJOR = 0x05;
			FIRST = (byte) (140 & 0xFF);
			SECOND = 0x00;
			THRID = 0x00;
			send();
		}
		public void xunji() {
			MAJOR = 0x06;
			FIRST = (byte) (sp_n & 0xFF);
			SECOND = 0x00;
			THRID = 0x00;
			send();
		}

	// 红外
		public void infrared_stereo(byte [] data){
			MAJOR = 0x10;
			FIRST =  (byte)0xff;
			SECOND = (byte)data[0];
			THRID = (byte)data[1];
			send();
			yanchi(500);
			MAJOR = 0x11;
			FIRST = (byte)data[2];
			SECOND = (byte)data[3];
			THRID = (byte)data[4];
			send();
			yanchi(500);
			MAJOR = 0x12;
			FIRST = 0x00;
			SECOND = 0x00;
			THRID = 0x00;
			send();
			yanchi(500);
		}
		// 蜂鸣器
		public void buzzer(int i) {
			if (i == 1)
				FIRST = 0x01;
			else if (i == 0)
				FIRST = 0x00;
			MAJOR = 0x30;
			SECOND = 0x00;
			THRID = 0x00;
			send();
		}

		// 指示灯
		public void light(int left, int right) {
			if (left == 1 && right == 1) {
				MAJOR = 0x20;
				FIRST = 0x01;
				SECOND = 0x01;
				THRID = 0x00;
				send();
			} else if (left == 1 && right == 0) {
				MAJOR = 0x20;
				FIRST = 0x01;
				SECOND = 0x00;
				THRID = 0x00;
				send();
			} else if (left == 0 && right == 1) {
				MAJOR = 0x20;
				FIRST = 0x00;
				SECOND = 0x01;
				THRID = 0x00;
				send();
			} else if (left == 0 && right == 0) {
				MAJOR = 0x20;
				FIRST = 0x00;
				SECOND = 0x00;
				THRID = 0x00;
				send();
			}
		}
		//语音播报
		public void send_voice(String src) {
			try {
				// 发送数据字节数组
				byte[] textbyte = bytesend(src.getBytes("GBK"));
				if (socket != null && !socket.isClosed()) {
					bOutputStream.write(textbyte, 0, textbyte.length);
					bOutputStream.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private byte[] bytesend(byte[] sbyte) {
			byte[] textbyte = new byte[sbyte.length + 5];
			textbyte[0] = (byte) 0xFD;
			textbyte[1] = (byte) (((sbyte.length + 2) >> 8) & 0xff);
			textbyte[2] = (byte) ((sbyte.length + 2) & 0xff);
			textbyte[3] = 0x01;// 合成语音命令
			textbyte[4] = (byte) 0x01;// 编码格式
			for (int i = 0; i < sbyte.length; i++) {
				textbyte[i + 5] = sbyte[i];
			}
			return textbyte;
		}
		
		// 沉睡
		public void yanchi(int time) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
