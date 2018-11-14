package com.example.car43;

import android.graphics.Bitmap;
import android.util.Log;

public class NumShiBieAgain {

	Bitmap bitmapInput;
	int width, height;
	int num[] = new int[200];

	public void getInt(int a[]) {
		num = a;
	}

	public int ShiBie(Bitmap bitmap) {
		bitmapInput = bitmap;
		width = bitmapInput.getWidth();
		height = bitmapInput.getHeight();

		// 1
		if (height > 4 * width) {
			return 1;
		}
		// 0
		else if (num[105] + num[104] + num[106] + num[103] + num[107] + num[95]
				+ num[94] + num[96] + num[93] + num[97] + num[85] + num[84]
				+ num[86] + num[83] + num[87] + num[115] + num[114] + num[116]
				+ num[113] + num[117] + num[125] + num[124] + num[126]
				+ num[123] + num[127] == 0) {
			return 0;
		}
		// 2
		else if (num[190] + num[191] + num[192] + num[193] + num[194]
				+ num[195] + num[196] + num[197] + num[198] + num[199] > 7) {
			return 2;
		}
		// 5 7 3
		else if (num[10] + num[11] + num[12] + num[13] + num[14] + num[15]
				+ num[16] + num[17] + num[18] + num[19] > 7) {
			// 5
			if (num[21] + num[31] + num[41] + num[51] + num[61] + num[71]
					+ num[81] + num[91] > 5) {
				return 5;
			}
			// 7
			else if (num[190] + num[191] + num[192] + num[193] + num[194]
					+ num[195] + num[196] + num[197] + num[198] + num[199] < 3) {
				return 7;
			}
			// 3
			else
				return 3;
		}
		// 4 9
		else if (num[190] + num[191] + num[192] + num[193] + num[194]
				+ num[195] + num[196] + num[197] + num[198] + num[199] < 4
				&& num[190] + num[191] + num[192] + num[193] + num[194]
						+ num[195] + num[196] + num[197] + num[198] + num[199] > 0) {
			if (num[9] + num[19] + num[29] + num[39] + num[49] + num[59] + num[69]
					 < 1
			// && num[10] + num[11] + num[12] + num[13] + num[14]
			// + num[15] + num[16] + num[17] + num[18] + num[19] < 4
							) {
				return 4;
			} else
				return 9;
		}
		// 6
		else if (num[10] + num[11] + num[12] + num[13] + num[14] + num[15]
				+ num[16] + num[17] + num[18] + num[19] < 4) {
			return 6;
		}
		// 8
		else if (num[10] + num[11] + num[12] + num[13] + num[14] + num[15]
				+ num[16] + num[17] + num[18] + num[19] > 3
				&& num[190] + num[191] + num[192] + num[193] + num[194]
						+ num[195] + num[196] + num[197] + num[198] + num[199] > 3) {
			return 8;
		}
		Log.e("1", num[10] + num[11] + num[12] + num[13] + num[14] + num[15]
				+ num[16] + num[17] + num[18] + num[19] + "");
		return -1;
	}

}
