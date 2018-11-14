package com.example.send;

import android.os.Handler;
import android.os.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class Fu {
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
	public int sp_n = 40, en_n = 125;
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
			// ���������ֽ�����
			byte[] sbyte = { 0x55, (byte) 0X05, MAJOR, FIRST, SECOND, THRID,(byte) CHECKSUM,(byte) 0xBB };
			bOutputStream.write(sbyte, 0, sbyte.length);
			bOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void chan() {
		MAJOR = (byte) 0x80;
		FIRST = 0x01;
		SECOND = 0x00;
		THRID = 0x00;
		send();
	}

	public void chan1() {
		MAJOR = (byte) 0x80;
		FIRST = 0x00;
		SECOND = 0x00;
		THRID = 0x00;
		send();
	}

	// ͣ��
	public void stop() {
		MAJOR = 0x01;
		FIRST = 0x00;
		SECOND = 0x00;
		THRID = 0x00;
		send();
	}

	public void qianku() {
		MAJOR = 0x02;
		FIRST = (byte) (sp_n & 0xFF);
		SECOND = (byte) (330 & 0xff);
		THRID = (byte) (330 >> 8);
		send();
	}

	// ����
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

	public void picture(int i) {// ͼƬ�Ϸ����·�
		if (i == 1)
			MAJOR = 0x50;
		else
			MAJOR = 0x51;
		FIRST = 0x00;
		SECOND = 0x00;
		THRID = 0x00;
		send();
	}

	public void gear(int i) {// ���յ�λ��
		if (i == 1)
			MAJOR = 0x61;
		else if (i == 2)
			MAJOR = 0x62;
		else if (i == 3)
			MAJOR = 0x63;
		FIRST = 0x00;
		SECOND = 0x00;
		THRID = 0x00;
		send();
	}

	public void qian() {
		MAJOR = 0x02;
		FIRST = (byte) (sp_n & 0xFF);
		SECOND = (byte) (en_n & 0xff);
		THRID = (byte) (en_n >> 8);
		send();
	}

	public void qian2() {
		MAJOR = 0x02;
		FIRST = (byte) (sp_n & 0xFF);
		SECOND = (byte) (70 & 0xff);
		THRID = (byte) (70 >> 8);
		send();
	}
	
	public void qian3() {
		MAJOR = 0x02;
		FIRST = (byte) (sp_n & 0xFF);
		SECOND = (byte) (400 & 0xff);
		THRID = (byte) (400 >> 8);
		send();
	}

	public void hou2() {
		int en_n1 = 90;
		MAJOR = 0x03;
		FIRST = (byte) (sp_n & 0xFF);
		SECOND = (byte) (en_n1 & 0xff);
		THRID = (byte) (en_n1 >> 8);
		send();
	}

	public void zuo() {
		MAJOR = 0x04;
		FIRST = (byte) (60 & 0xFF);
		SECOND = 0x00;
		THRID = 0x00;
		send();
	}

	public void you() {
		MAJOR = 0x05;
		FIRST = (byte) (60 & 0xFF);
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

	public void arm(byte daoda) {// ��е��
		MAJOR = 0x70;
		FIRST = 0x02;
		SECOND = (byte) daoda;
		THRID = 0x00;
		send();
	}

	public void arm_reset() {
		try {
			CHECKSUM = (short) ((0x72 + 0x07 + 0x00 + 0x00) % 256);
			// ���������ֽ�����
			byte[] sbyte = { 0x55, (byte) 0X05, 0x72, 0x07, 0x00, 0x00,
					(byte) CHECKSUM, (byte) 0xBB };
			bOutputStream.write(sbyte, 0, sbyte.length);
			bOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ��˯
	public void yanchi(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
