package com.example.car43;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.nfc.FormatException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.send.Fu;
import com.example.send.LED;
import com.example.send.Walk;
import com.example.send.Zha;
import com.example.send.Zhu;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
	Zhu zhu;
	Fu fu;
	Button but1;
	TextView text,text1,text2,text3,text4;
	ImageView imgv,imgv2;
	LCDCut lcdCut;
	Bitmap bmpTemp;
	Drawable sp;
	Zha zha;
	LED led;
	Walk walk;
	character big;
	
	
	private String ip="192.168.37.254" ,CarIP="192.168.37.123:81";
	private long WalkState = 1;//运动状态
	private long psStatus = 0;// 光敏状态
	private long UltraSonic = 0;// 超声波
	private long Light = 0;// 光照
	private long CodedDisk = 0;// 码盘值
	private long psStatus1 = 1;// 状态
	private long UltraSonic1 = 1;// 超声波
	private long Light1 = 1;// 光照
	private byte[] mByte = new byte[12];
	private IThread iThread = null;
	private imageProcessing imgp;
	int k = 3;
	Bitmap bt;
	Bitmap bt2;
	private String address;
	private String str,erwei;
	WifiManager wifiManager;
	int colour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but1=(Button) findViewById(R.id.button1);
        text = (TextView) findViewById(R.id.textView1);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        imgv=(ImageView) findViewById(R.id.imageView1);     
        imgv2=(ImageView) findViewById(R.id.imageView2);
        zhu = new Zhu();
        led = new LED();
        zha = new Zha();
        fu = new Fu();
		walk = new Walk();
        lcdCut = new LCDCut();
        imgp=new imageProcessing();
        
//        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//		dhcpInfo = wifiManager.getDhcpInfo();
//		ip = Formatter.formatIpAddress(dhcpInfo.gateway);
		Log.e("小车IP值：", ip);
        new Thread(networkTask).start();
        but1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				text.setText("调试");
			
//				if (iThread == null) {
//					iThread = new IThread();
//					iThread.start();
//				}
				new IThread().start();
			}
		});
    }
    
    private Handler rehHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				mByte = (byte[]) msg.obj;
				if (mByte[0] == (byte) 0x55 && mByte[1] == (byte) 0xaa) {
					//运动状态
					WalkState = mByte[2] & 0xff;
					// 光敏状态
					psStatus = mByte[3] & 0xff;
					// 超声波数据
					UltraSonic = mByte[5] & 0xff;
					UltraSonic = UltraSonic << 8;
					UltraSonic += mByte[4] & 0xff;
					// 光照强度
					Light = mByte[7] & 0xff;
					Light = Light << 8;
					Light += mByte[6] & 0xff;
					// 码盘
					CodedDisk = mByte[9] & 0xff;
					CodedDisk = CodedDisk << 8;
					CodedDisk += mByte[8] & 0xff;
					// 显示数据
					text.setText("状态："+psStatus+" 超声波："+UltraSonic+" 光照强度："+Light);
					text1.setText(String.valueOf(mByte[10]));
				}
			}
		};
	};
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				text1.setText("蓝色");
				break;
			case 2:
				imgv.setImageBitmap((Bitmap)msg.obj);
				break;
			case 3:
				text2.setText("42");
				break;
			case 4:
				imgv2.setImageBitmap(bt);
				break;
			case 5:
				text3.setText((String)msg.obj);
				//text4.setText("应进入"+(String)msg.obj+"号库");
				break;
			}
		}
	};
	
    Runnable networkTask = new Runnable() {
		public void run() {
			zhu.connect(rehHandler, ip);
			fu.connect(rehHandler, ip);
			led.connect(rehHandler, ip);
			zha.connect(rehHandler, ip);
			walk.connect(rehHandler, ip);
		}
	};
    
    class IThread extends Thread {
		public void run() {
			try {

				zhu.send_voice("开始任务");
				zhu.yanchi(1000);

				led.digital();
				zhu.yanchi(1000);

				zha.gate(1);
				zhu.yanchi(1500);

				zhu.xunji();
				zhu.yanchi(4000);

				zhu.qian();
				zhu.yanchi(1000);
				zhu.xunji();
				zhu.yanchi(5000);

				zhu.qian();
				zhu.yanchi(1000);
				zhu.xunji();
				zhu.yanchi(5000);

				zhu.qian();
				zhu.yanchi(1000);
				zhu.xunji();
				zhu.yanchi(5000);

				bt = paiZhao();
				Message msg3 = new Message();
				msg3.what = 2;
				msg3.obj = bt;
				handler.sendMessage(msg3);
				String er;
				er = erWeiMa(bt);

				String str = er;
				String[] strs=str.split(",");

				String Color = new String();
				Color  = strs[0].toString();
				String X = new String();
				X = strs[1].toString();
				String Y = new String();
				Y = strs[2].toString();

				System.out.println(Color);
				System.out.println(X);
				System.out.println(Y);

				String color = new String();
				color = Color.substring(3);
				String x = new String();
				x = X.substring(2);
				String y = new String();
				y = Y.substring(2);

				System.out.println(color);
				System.out.println(x);
				System.out.println(y);

				BigInteger xx = new BigInteger(x);
				BigInteger yy = new BigInteger(y);
				BigInteger z = yy.add(xx);
				BigInteger m = new BigInteger("79");


				BigInteger result[]=z.divideAndRemainder(m);
				System.out.println(result[1]);
				String sssss=result[1].toString();
				Message msg4 = new Message();
				msg4.what = 3;
				msg4.obj = sssss;
				handler.sendMessage(msg4);
				zhu.yanchi(3000);

				zhu.hou3();
				zhu.yanchi(3000);
				zhu.you();
				zhu.yanchi(2000);
				zhu.xunji();
				zhu.yanchi(7000);

				zhu.qian();
				zhu.yanchi(2000);
				zhu.zuo();
				zhu.yanchi(7000);
				zhu.xunji();
				zhu.yanchi(4000);


				bt2 = paiZhao();
				Message msg5 = new Message();
				msg5.what = 4;
				msg5.obj = bt2;
				handler.sendMessage(msg5);
				int a=imageProcessing.bt(bt2);
				String yanse;
				yanse = new String();
				switch(a)
				{
					case 1:
						yanse = "红色";
						break;
					case 2:
						yanse = "绿色";
						break;
					case 3:
						yanse = "蓝色";
						break;
					case  4:
						yanse = "白色";
						break;
					case 5:
						yanse = "黑色";
						break;
				}
			for(int i=0;i<5;i++)
			{
				if(yanse!=color){
					zhu.picture(1);
					bt2 = paiZhao();
					Message msg6 = new Message();
					msg6.what = 4;
					msg6.obj = bt2;
					handler.sendMessage(msg6);
					int A=imageProcessing.bt(bt2);
					yanse = new String();
					switch(A)
					{
						case 1:
							yanse = "红色";
							break;
						case 2:
							yanse = "绿色";
							break;
						case 3:
							yanse = "蓝色";
							break;
						case  4:
							yanse = "白色";
							break;
						case 5:
							yanse = "黑色";
							break;
					}
				}
				else break;
			}
			zhu.yanchi(3000);

			zhu.hou3();
			zhu.yanchi(3000);
			zhu.you();
			zhu.yanchi(3000);
			zhu.xunji();
			zhu.yanchi(8000);


			zhu.qian();
			zhu.yanchi(2000);
			zhu.zuo();
			zhu.yanchi(3000);
			zhu.xunji();
			zhu.yanchi(4000);

			int g;
			g = guangzhao();
			zhu.yanchi(3000);
			zhu.send_voice("当前档位为");
			zhu.yanchi(3000);
			if(g==1)
			{
				zhu.send_voice("1档");
				zhu.yanchi(3000);
			}
			else if(g==2)
			{
				zhu.send_voice("2档");
				zhu.yanchi(3000);
			}
			else if (g==4){
				zhu.send_voice("4挡");
				zhu.yanchi(3000);
			}
			else
			{
				zhu.send_voice("3档");
				zhu.yanchi(3000);
			}


			if(g==1){
				zhu.hou3();
				zhu.yanchi(3000);
				zhu.you();
				zhu.yanchi(2000);
				zhu.qian2();
				zhu.yanchi(3000);
			}
			else if(g==2)
			{
				zhu.hou3();
				zhu.yanchi(3000);
				zhu.hou3();
				zhu.yanchi(2000);
				zhu.you();
				zhu.yanchi(4000);
				zhu.xunji();
				zhu.yanchi(3000);
				zhu.qian();
				zhu.yanchi(2000);
				zhu.xunji();
				zhu.yanchi(5000);
				zhu.zuo();
				zhu.yanchi(2000);
				zhu.qian2();
				zhu.yanchi(2000);

			}
			else if (g==4){
				zhu.hou3();
				zhu.yanchi(3000);
				zhu.hou3();
				zhu.yanchi(2000);
				zhu.you();
				zhu.yanchi(4000);
				zhu.xunji();
				zhu.yanchi(5000);
				//zhu.send_voice("啊啊啊我要往前走路好远");
				zhu.yanchi(1000);

				zhu.qian2();
				zhu.yanchi(2000);
				zhu.xunji();
				zhu.yanchi(5000);

				zhu.qian2();
				zhu.yanchi(2000);
				zhu.xunji();
				zhu.yanchi(5000);

				zhu.zuo();
				zhu.yanchi(2000);
				zhu.qian2();
				zhu.yanchi(2000);




			}
			else
			{
				zhu.hou3();
				zhu.yanchi(2000);
				zhu.hou3();
				zhu.yanchi(2000);
				zhu.you();
				zhu.yanchi(4000);
				zhu.xunji();
				zhu.yanchi(3000);
				zhu.qian();
				zhu.yanchi(2000);
				zhu.xunji();
				zhu.yanchi(5000);
				zhu.qian();
				zhu.yanchi(2000);
				zhu.zuo();
				zhu.yanchi(2000);
				zhu.qian2();
				zhu.yanchi(2000);

			}





				zhu.cixuanfu();
				zhu.yanchi(1000);





				zhu.buzzer(1);//蜂鸣器
				zhu.yanchi(5000);


				zhu.send_voice("任务结束");
				zhu.buzzer(0);
				zhu.yanchi(1000);
				led.digital3();


			/*	walk.MarkSendstar89888t();

				if (mByte[10]==15)
				{
					bt = paiZhao();
					Message msg3 = new Message();
					msg3.what = 3;
					msg3.obj = bt;
					handler.sendMessage(msg3);
					String er;
					BigInteger X,Y,Z,san;
					er = erWeiMa(bt);
					big = new character(er);
					System.out.print(er);
					X = new BigInteger(big.getX());
					System.out.print(big.getX());
					Y = new BigInteger(big.getX());
					System.out.print(big.getX());
					Z = new BigInteger("123465798123465799");
					System.out.print(big.getYunsuan());
					if(big.getYunsuan()=="+")
					{
						Z = X.subtract(Y);
					}
					else
					{
						Z = X.add(Y);
					}
					System.out.print(Z);
					Message msg1 = new Message();
					msg1.what = 1;
					msg1.obj = er;
					handler.sendMessage(msg1);
					Message msg2 = new Message();
					msg2.what = 2;
					msg2.obj = "X:"+X+"Y:"+Y+"Z:"+Z;
					handler.sendMessage(msg2);
				}*/
				
			}catch (Exception e) {
				// TODO: handle exception
			}	
		}

		private int BigInteger(BigInteger y) {
			// TODO Auto-generated method stub
			return 0;
		}
   }
    
    public String getBigint(String er){
    	String Y="0";
    	int l;
    	String s = new String(er);
    	l = s.length();
    	for(int i=0;i<l;i++){
    		if(s.charAt(i)>='0'&&s.charAt(i)<='9')
    			Y = Y + s.charAt(i);
    	}
    	return Y;
    }
    
    public int guangzhao(){
		int g = 0;
		if(Light < 100)
			g = 0;
		else if (Light <250)
			g = 1;
		else if (Light<490)
			g = 2;
		else if (Light <700)
			g = 3;
		else if(Light <1000)
			g = 4;
		return g;
    }
    
    public int yanse(String er){
    	char a;
    	a = er.charAt(0);
    	if(a == '红')
    		return 1;
    	else if(a == '绿')
    		return 2;
    	else if(a == '蓝')
    		return 3;
    	else if(a == '黄')
    		return 4;
    	else if(a == '紫')
    		return 5;
    	else if(a == '青')
    		return 6;
    	else if(a == '黑')
    		return 7;
    	else if(a == '白')
    		return 8;
		return 0;
    }
    
    public int xingzhuang(String er){
    	char a;
    	a = er.charAt(2);
    	if(a == '矩')
    		return 1;
    	else if(a == '圆')
    		return 2;
    	else if(a == '三')
    		return 3;
    	else if(a == '菱')
    		return 4;
    	else if(a == '梯')
    		return 5;
    	else if(a == '饼')
    		return 6;
    	else if(a == '靶')
    		return 7;
    	else if(a == '条')
    		return 8;
		return 0;
    }
		
    public Bitmap paiZhao() {
		String address = "http://" + CarIP
				+ "/snapshot.cgi?user=admin&pwd=888888";
		try {

			byte[] data = WebService.getImage(address); // 得到图片的输入流

			// 二进制数据生成位图
			Bitmap bit = BitmapFactory.decodeByteArray(data, 0, data.length);

			// 给ImageView设置图片
			// im.setImageBitmap(bit);

			return bit;

		} catch (Exception e) {
			Log.e("NetActivity", e.toString());

			return null;
		}
	}
    
    private Result result;
    public String erWeiMa(Bitmap b) throws FormatException {

		RGBLuminanceSource source = new RGBLuminanceSource(b);
		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
		Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
		QRCodeReader reader = new QRCodeReader();

		try {
			result = reader.decode(bitmap1, hints);
			str = result.toString();

		} catch (com.google.zxing.NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		} catch (ChecksumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (com.google.zxing.FormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		System.out.println(str);
		return str;
	}
    
    public int juli(){
    	int num = 0,max = 0,min = 100000,n = 0,i=0;
    	for(i=0;i<10;i++){
    		n = (int) UltraSonic;
    		if(n > max)
    			max = n;
    		if(n < min)
    			min = n;
    		num += n;
    	}
    	num -= max;
    	num -= min;
    	num /= (i-2);
		return num;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
