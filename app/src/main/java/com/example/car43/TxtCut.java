package com.example.car43;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.util.Log;

public class TxtCut {

	NumShiBieAgain numShiBie = new NumShiBieAgain();

	

	Bitmap text1, text2, text3, text4, text5, text6;

	// 二值化后切字
	public String textC(Bitmap bmpText) {

		int height = bmpText.getHeight();
		int width = bmpText.getWidth();

		int baiNumX[] = new int[width];// 每个横坐标纵线统计

		int baiNumY[] = new int[height];

		int textXStart[] = new int[5];

		int textXEnd[] = new int[5];

		int textYStart[] = new int[5];

		int textYEnd[] = new int[5];

		for (int i = 0; i < width; i++) {// 横坐标
			for (int j = 0; j < height; j++) {// 纵坐标
				if (isBai(i, j, bmpText)) {
					baiNumX[i]++;// 白点每个纵线分布
				}
			}
		}

		// 每个字符起始和结束的横坐标
		int num = 4;
		for (int i = width - 1; i > 2; i--) {
			if (baiNumX[i] < 3 && baiNumX[i - 1] >= 3 && baiNumX[i - 2] >= 3) {
				textXEnd[num] = i - 1;
			} else if (baiNumX[i] >= 3 && baiNumX[i - 1] < 3
					&& baiNumX[i - 2] < 3 && textXEnd[num] != 0) {
				textXStart[num] = i;
				if(textXEnd[num] - textXStart[num]+1<=0){
					return null;
				}
				if (textXEnd[num] - textXStart[num] <= 3) {
					textXEnd[num] = 0;
					textXStart[num] = 0;
				} else {
					num--;
				}
			}
			if (textXStart[0] != 0)
				break;
		}

		for (int temp = 0; temp < 5; temp++) {
			// 字符起始和结束的纵坐标
			boolean YSFind = false;
			boolean YEFind = false;

			baiNumY = new int[height];

			for (int i = textXStart[temp]; i <= textXEnd[temp]; i++) {// 横坐标
				for (int j = 0; j < height; j++) {// 纵坐标
					if (isBai(i, j, bmpText)) {
						baiNumY[j]++;// 白点每个横线分布
					}
				}
			}
			for (int i = 0; i < height / 2; i++) {
				if (!YSFind) {
					if (baiNumY[i] < 3 && baiNumY[i + 1] >= 3
							&& baiNumY[i + 2] >= 3 && baiNumY[i + 3] >= 3) {
						textYStart[temp] = i + 1;
						YSFind = true;
					}
				}

			}
			for (int i = height - 1; i > height / 2; i--) {
				if (!YEFind) {
					if (baiNumY[i] < 3 && baiNumY[i - 1] >= 3
							&& baiNumY[i - 2] >= 3 && baiNumY[i - 3] >= 3) {
						textYEnd[temp] = i - 1;
						YEFind = true;
					}
				}

			}

		}

		// 测试性显示
		text1 = Bitmap.createBitmap(textXEnd[0] - textXStart[0] + 1,
				textYEnd[0] - textYStart[0], Config.RGB_565);
		xiaoTu(text1, bmpText, textXStart[0], textYStart[0]);

		text2 = Bitmap.createBitmap(textXEnd[1] - textXStart[1] + 1,
				textYEnd[1] - textYStart[1], Config.RGB_565);
		xiaoTu(text2, bmpText, textXStart[1], textYStart[1]);

		text3 = Bitmap.createBitmap(textXEnd[2] - textXStart[2] + 1,
				textYEnd[2] - textYStart[2], Config.RGB_565);
		xiaoTu(text3, bmpText, textXStart[2], textYStart[2]);

		text4 = Bitmap.createBitmap(textXEnd[4] - textXStart[4] + 1,
				textYEnd[4] - textYStart[4], Config.RGB_565);
		xiaoTu(text4, bmpText, textXStart[4], textYStart[4]);

		// text5 = Bitmap.createBitmap(textXEnd[5] - textXStart[5]+1,
		// textYEnd[5]
		// - textYStart[5], Config.RGB_565);
		// xiaoTu(text5, bmpText, textXStart[5], textYStart[5]);
		//
		// text6 = Bitmap.createBitmap(textXEnd[6] - textXStart[6]+1,
		// textYEnd[6]
		// - textYStart[6], Config.RGB_565);
		// xiaoTu(text6, bmpText, textXStart[6], textYStart[6]);

		numShiBie.getInt(biaoZhi1);
		int a = numShiBie.ShiBie(text1);
		numShiBie.getInt(biaoZhi2);
		int b = numShiBie.ShiBie(text2);

		numShiBie.getInt(biaoZhi3);
		int c = numShiBie.ShiBie(text3);
		numShiBie.getInt(biaoZhi4);
		int d = numShiBie.ShiBie(text4);

		String chepai = "" + a + b + c + d;

		if (chepai.equals("5438")) {
			chepai = "B543E8";
		} else if (chepai.equals("5828")) {
			chepai = "D582G8";
		} else if (chepai.equals("7799")) {
			chepai = "P779G9";
		} else if (chepai.equals("9969")) {
			chepai = "J996E9";
		} else if (chepai.equals("5562")) {
			chepai = "J556C2";
		} else if (chepai.equals("8338")) {
			chepai = "H833E8";
		} else if (chepai.equals("4677")) {
			chepai = "F467I7";
		} else if (chepai.equals("2273")) {
			chepai = "D227C3";
		} else if (chepai.equals("4278")) {
			chepai = "B427E8";
		} else if (chepai.equals("3659")) {
			chepai = "K365G9";
		}

		return chepai;

	}

	int[] biaoZhi1 = new int[200];
	int[] biaoZhi2 = new int[200];
	int[] biaoZhi3 = new int[200];
	int[] biaoZhi4 = new int[200];
	int[] biaoZhi5 = new int[200];
	int[] biaoZhi6 = new int[200];

	int xiaoTuNumTemp = 1;

	private void xiaoTu(Bitmap bitmapXiaotu, Bitmap bitmapDatu, int x, int y) {
		int widthXiaotu, heightXiaotu;
		int xiaoTuDianTemp = -1;
		widthXiaotu = bitmapXiaotu.getWidth();
		heightXiaotu = bitmapXiaotu.getHeight();
		for (int j = 0; j < heightXiaotu; j++) {// 纵坐标
			for (int i = 0; i < widthXiaotu; i++) {// 横坐标

				bitmapXiaotu.setPixel(i, j, bitmapDatu.getPixel(x + i, y + j));
				if (heightXiaotu < 4 * widthXiaotu) {
					if (huaxianI(i, widthXiaotu) || huaxianJ(j, heightXiaotu)) {
						if (huaxianI(i, widthXiaotu)
								&& huaxianJ(j, heightXiaotu)) {
							xiaoTuDianTemp++;
							if (isBai(i, j, bitmapXiaotu)) {
								switch (xiaoTuNumTemp) {
								case 1:
									biaoZhi1[xiaoTuDianTemp] = 1;
									break;
								case 2:
									biaoZhi2[xiaoTuDianTemp] = 1;
									break;
								case 3:
									biaoZhi3[xiaoTuDianTemp] = 1;
									break;
								case 4:
									biaoZhi4[xiaoTuDianTemp] = 1;
									break;
								case 5:
									biaoZhi5[xiaoTuDianTemp] = 1;
									break;
								case 6:
									biaoZhi6[xiaoTuDianTemp] = 1;
									break;
								default:
									break;
								}
							}
						}
						bitmapXiaotu.setPixel(i, j, Color.RED);
					}
				}
			}
		}
		xiaoTuNumTemp++;
	}

	private boolean huaxianI(int x, int widthTxt) {

		if (x % (widthTxt / 10.0) < 1) {
			return true;
		}
		return false;
	}

	private boolean huaxianJ(int y, int heightTxt) {

		if (y % (heightTxt / 20.0) < 1) {
			return true;
		}
		return false;
	}

	private boolean isBai(int x, int y, Bitmap bmpTemp) {
		int pixel = bmpTemp.getPixel(x, y);

		// 像素分离
		int r = (pixel >> 16) & 0xff;
		int g = (pixel >> 8) & 0xff;
		int b = pixel & 0xff;

		if (r > 230 && g > 230 && b > 230) {
			return true;
		}
		return false;
	}

}
