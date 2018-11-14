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

public class Zha {
	private int port = 60000;
	private DataInputStream bInputStream;
	private DataOutputStream bOutputStream;
	private Socket socket;
	private byte[] rbyte = new byte[512];
	private Timer timer;
	public short CHECKSUM=0x00;
	public short TYPE=0x02;
	public byte MAJOR = 0x00;
	public byte FIRST = 0x00;
	public byte SECOND = 0x00;
	public byte THRID = 0x00;
	public int sp_n = 80, en_n = 70;
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

	Handler reHandler;
	public void connect(final Handler handler, String IP) {

		try {
			this.reHandler=handler;
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
			byte[] sbyte = { 0x55, (byte) TYPE, MAJOR, FIRST, SECOND, THRID,
					(byte) CHECKSUM, (byte) 0xBB };
			bOutputStream.write(sbyte, 0, sbyte.length);
			bOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void gate(int i){//闸门
		byte type=(byte) TYPE;
		if(i==1){//open
			TYPE = 0x03;
			MAJOR = 0x01;
			FIRST = 0x01;
			SECOND = 0x00;
			THRID = 0x00;
			send();
		}
		else if(i==2){//close
			TYPE = 0x03;
			MAJOR = 0x01;
			FIRST = 0x02;
			SECOND = 0x00;
			THRID = 0x00;
			send();
		}
		TYPE = type;
	}
}


