package com.example.car43;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;

//按照白框切除
public class LCDCut {

	
	Bitmap changeBit = Bitmap.createBitmap(640, 480, Config.RGB_565);

	public Bitmap ColorChange1(Bitmap bitmap) {

		// Bitmap changeBit = Bitmap.createBitmap(640, 480, Config.RGB_565);
		
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		int baiHeng[] = new int[480];
		int baiZong[] = new int[640];

		for (int i = 0; i < width; i++) {// 横坐标

			for (int j = 0; j < height; j++) {// 纵坐标
				// 得到图片上点（i，j）的像素
				int pixel = bitmap.getPixel(i, j);
				// 像素分离
				int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = pixel & 0xff;
				// Log.e("color", r+"\n"+g+"\n"+b);
				// 判断颜色并赋值
				if (r > 200 && g > 200 && b > 220) {
					baiHeng[j]++;
					baiZong[i]++;
					changeBit.setPixel(i, j, Color.WHITE);
				} else {
					changeBit.setPixel(i, j, (r << 16 | g << 8 | b));
				}
			}
		}

		
		
		//320*180
		int temp1 = baiHeng[0], max1 = 0, temp2 = baiHeng[479], max2 = 479;

		for (int x = 0; x < 240; x++) {
			if (temp1 < baiHeng[x + 1]) {
				temp1 = baiHeng[x + 1];
				max1 = x + 1;
			}
		}
		for (int x = 479; x > 240; x--) {
			if (temp2 < baiHeng[x - 1]) {
				temp2 = baiHeng[x - 1];
				max2 = x - 1;
			}
		}

		for (int x = (max2 + max1) / 2; x > max1; x--)
			if (baiHeng[x] > baiHeng[max1] / 5 * 4) {
				max1 = x;
				break;
			}

		for (int x = (max2 + max1) / 2; x < max2; x++)
			if (baiHeng[x] > baiHeng[max2] / 5 * 4) {
				max2 = x;
				break;
			}

		 for(int x=0;x<640;x++)
		 {
		 changeBit.setPixel(x, max1, Color.RED);
		 changeBit.setPixel(x, max2, Color.RED);
		
		 }

		int temp3 = baiZong[0], max3 = 0, temp4 = baiZong[639], max4 = 639;

		for (int y = 0; y < 320; y++) {
			if (temp3 < baiZong[y + 1]) {
				temp3 = baiZong[y + 1];
				max3 = y + 1;
			}
		}
		for (int y = 639; y > 320; y--) {
			if (temp4 < baiZong[y - 1]) {
				temp4 = baiZong[y - 1];
				max4 = y - 1;
			}
		}



		/*for (int x = (max3 + max4) / 3; x > max3; x--)
			if (baiZong[x] > baiZong[max3] / 5 * 4) {
				max3 = x;
				break;
			}

		for (int x = (max3 + max4) / 2; x < max4; x++)
			if (baiZong[x] > baiZong[max4] / 5 * 4) {
				max4 = x;
				break;
			}*/

		// 竖线
		 for(int y=0;y<480;y++)
		 {
		 changeBit.setPixel(max3, y, Color.YELLOW);
		 changeBit.setPixel(max4, y, Color.YELLOW);
		
		 }

		// 图片裁切留下中间

		Bitmap changeBit1 = Bitmap.createBitmap(max4 - max3 - 24, max2 - max1
				- 29, Config.RGB_565);

		for (int x = 0; x < 640; x++)
			for (int y = 0; y <480; y++) {
				if (x >= max3 + 15 && x <= max4 - 12 && y >= max1 + 15
						&& y <= max2 - 15) {
					changeBit1.setPixel(x - max3 - 15, y - max1 - 15,
							changeBit.getPixel(x, y));
				}
			}

		return changeBit1;
	}
}
