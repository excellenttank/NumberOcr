package com.excellent_tank.numberocr;

import android.graphics.Bitmap;
import android.os.Environment;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

/**
 * 作者：WangBinBin on 8/29 17:47
 * 邮箱：1205998131@qq.com
 */

public class OCR {
    private TessBaseAPI mTess;
    private boolean flag;
    public OCR() {
        // TODO Auto-generated constructor stub

        mTess = new TessBaseAPI();
        flag = true;
        String datapath = Environment.getExternalStorageDirectory() + "/tesseract/";
        String language = "num";
        //请将你的语言包放到这里 sd 的 tessseract 下的tessdata 下
        File dir = new File(datapath + "tessdata/");
        if (!dir.exists())
            dir.mkdirs();
        mTess.setDebug(true);
        flag = mTess.init(datapath, language);

        System.out.println("initResult:" + flag);

    }

    /**
     * 识别出来bitmap 上的文字
     *
     * @param bitmap 需要识别的图片
     * @return
     */
    public String getOCRResult(Bitmap bitmap) {
        String result = "";
        if (flag) {
            mTess.setImage(bitmap);
            result = mTess.getUTF8Text();
            System.out.println("result:" + result);
        }

        return result;
    }

    public void onDestroy() {
        if (mTess != null)
            mTess.end();
    }

}
