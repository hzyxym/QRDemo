package com.phone580.qrdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * 二维码工具类
 * Created by ling on 2015/8/20.
 */
public class ZXingUtil {

    public static int QRCODE_SIZE = 300;       //生成二维码图片大小
    public static Bitmap createQRCodeBitmap(Activity activity, String content) {
        return createQRCodeBitmap(activity, content, -1);
    }
    /**
     * 创建QR二维码图片
     */
    public static Bitmap createQRCodeBitmap(Activity activity, String content, int gapColor) {
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性

        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        //窗口的宽度
        int screenWidth = dm.widthPixels;
        QRCODE_SIZE =screenWidth*3/4;
//        QRCODE_SIZE = dip2px(FBSApplication.getAppContext(),screenWidth*2/3);
        // 用于设置QR二维码参数
        Hashtable<EncodeHintType, Object> qrParam = new Hashtable<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别——这里选择最高H级别
        qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // 生成QR二维码数据——这里只是得到一个由true和false组成的数组
        // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, qrParam);

            //1.1去白边

            int[] rec = bitMatrix.getEnclosingRectangle();

            int resWidth = rec[2] + 1;

            int resHeight = rec[3] + 1;

            BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
            resMatrix.clear();
            for (int i = 0; i < resWidth; i++) {
                for (int j = 0; j < resHeight; j++) {
                    if (bitMatrix.get(i + rec[0], j + rec[1])) {
                        resMatrix.set(i, j);
                    }
                }
            }
            // 开始利用二维码数据创建Bitmap图片，分别设为黑白两色
            int w = resMatrix.getWidth();
            int h = resMatrix.getHeight();
            QRCODE_SIZE = w;
            int[] data = new int[w * h];

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (resMatrix.get(x, y))
                        data[y * w + x] = 0xff000000;// 黑色
                    else
                        data[y * w + x] = gapColor;// -1 相当于0xffffffff 白色
                }
            }
                // 创建一张bitmap图片，采用最高的效果显示
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            // 将上面的二维码颜色数组传入，生成图片颜色
            bitmap.setPixels(data, 0, w, 0, 0, w, h);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


}
