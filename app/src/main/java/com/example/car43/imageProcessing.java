package com.example.car43;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class imageProcessing {
	//public Bitmap bmpReturn, bmpXiao;

	public static int bt(Bitmap btm) {
		int width = btm.getWidth();
		int height = btm.getHeight();
		Bitmap bmpXiao = Bitmap.createBitmap(width, height, Config.RGB_565);
//		int LanHeng[] = new int[height];
//		int LanZong[] = new int[width];
		int rn=0,bn=0,gn=0,max=0,bk=0,wt=0;
		for (int i = 0; i < width; i++) {// ºá×ø±ê
			for (int j = 0; j < height; j++) {// ×Ý×ø±ê
				int pixel = btm.getPixel(i, j);
				// ÏñËØ·ÖÀë
				
				int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = pixel & 0xff;
				int k = pixel & 0xff;
				int w = pixel & 0xff;
				//bmpXiao.setPixel(i, j, btm.getPixel(i, j));
				if (b>160&&g<130&&r<110) {
					bmpXiao.setPixel(i,j,0x0000ff);
					bn++;//ÂÌ
				}else if(r>120&&g<110&&b<110){
					bmpXiao.setPixel(i,j,0xff0000);
					rn++;//ºì
				}else if(g>160&&r<150&&b<150) {
					bmpXiao.setPixel(i, j, 0x00ff00);
					gn++;//À¶
				}else if(g>200&&r<200&&b<200) {
					bmpXiao.setPixel(i, j, 0x000000);
					bk++;
				}else if(g>200&&r<200&&b<200) {
					bmpXiao.setPixel(i, j, 0xffffff);
					wt++;
				}else bmpXiao.setPixel(i,j,0x000000);
				
			}
		}
		if(bn>rn && bn>gn && bn>bk && bn>wt) max=3;//À¶É«
		if(rn>bn && rn>gn && rn>bk && rn>wt) max=1;//ºìÉ«
		if(gn>rn && gn>bn && gn>bk && gn>wt) max=2;//ÂÌÉ«
		if(bk>rn && bk>bn && bk>gn && bk>wt) max=4;//ºÚÉ«
		if(wt>rn && wt>bn && wt>bk && wt>gn) max=5;//°×É«

		return max;
	}
}
