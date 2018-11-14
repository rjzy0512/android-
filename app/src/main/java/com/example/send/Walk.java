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

public class Walk {
    private int port = 60000;
    private DataInputStream InputStream;
    private DataOutputStream OutputStream;
    private Socket socket;
    private byte[] rbyte = new byte[512];
    private long WalkState =1;//运动状态
    public short CHECKSUM=0x00;
    public byte MAJOR = 0x00;
    public byte FIRST = 0x00;
    public byte SECOND = 0x00;
    public byte THRID = 0x00;
    private Timer timer;
    private Thread reThread = new Thread(new Runnable() {
        @Override
        public void run() {
            // TODO Auto1-generated method stub
            while (socket != null && !socket.isClosed()) {
                try {
                    InputStream.read(rbyte);
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
            InputStream = new DataInputStream(socket.getInputStream());
            OutputStream = new DataOutputStream(socket.getOutputStream());
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

    public void MarkSend() {             //传输信号
        try {
            MAJOR = (byte) 0x01;
            CHECKSUM=(short) ((MAJOR+FIRST+SECOND+THRID)%256);
            // 发送数据字节数组
            byte[] sbyte = { 0x55, (byte) 0X0AA, MAJOR, FIRST, SECOND, THRID,(byte) CHECKSUM,(byte) 0xBB };
            OutputStream.write(sbyte, 0, sbyte.length);
            OutputStream.flush();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void MarkSendstart() {             //传输信号
        try {
            MAJOR = (byte) 0x01;
            CHECKSUM=(short) ((MAJOR+FIRST+SECOND+THRID)%256);
            // 发送数据字节数组
            byte[] sbyte = { 0x56, (byte) 0X0AA, MAJOR, FIRST, SECOND, THRID,(byte) CHECKSUM,(byte) 0xBB };
            OutputStream.write(sbyte, 0, sbyte.length);
            OutputStream.flush();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
