package com.example.mac.back.utils;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import com.example.mac.back.application.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by mac on 2018/3/24.
 */

public class BitmpUtils {

    /**
     * 采样率压缩  按照图片宽高自动计算缩放比，图片质量有保障
     *
     * @param filePath  设置宽高并不是设置图片实际宽高，而是根据宽高自动计算缩放比，压缩后图片不会变形，宽高会根据计算的缩放比同时缩放，
     *                  宽高建议都设置300   设置300后图片大小为100-200KB，图片质量能接受；设置为400到500，图片大小为500-600kb，上传偏大，可自行设置
     * @param reqHeight
     * @param reqWidth
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int reqHeight, int reqWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        //计算图片的缩放值
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     *这个可以压缩到指定的宽，但是图片大小可能达不到预期，图片本身较小的可以使用，图片较大的建议使用上一个压缩方式
     * 根据自定义宽度设置图片大小，高度自适应  0不压缩
     *
     * @param path
     * @param width
     * @return
     */
    public static Bitmap createScaledBitemap(String path, int width) {
        Bitmap bit = BitmapFactory.decodeFile(path);
        int bitWidth = bit.getWidth();//得到图片宽
        float scaleWidth = ((float) width) / ((float) bitWidth);//计算宽度缩放比例
        if (width == 0) {
            return bit;
        } else {
            int height = (int) (bit.getHeight() * scaleWidth);//根据宽度缩放比例设置高度
            Bitmap bitmap = Bitmap.createScaledBitmap(bit, width, height, true);
            return bitmap;
        }
    }

    /**
     *这是个保存Bitmap到sd卡中的方法，可以返回保存图片的路径
     * 保存Bitmap到sd
     *
     * @param mBitmap
     * @param bitName 图片保存的名称，返回存储图片的路径
     */
    public static String saveBitmap(Bitmap mBitmap, String bitName) {
        File f;
        //判断是否有sd卡 有就保存到sd卡，没有就保存到app缓存目录
        if (isStorage()) {
            File file = new File("/data/data/name");//保存的路径
            if (!file.exists()) {//判断目录是否存在
                file.mkdir();//不存在就创建目录
            }
            f = new File(file, bitName + ".jpg");
        } else {
            File file = new File(MyApplication.getsInstance().getCacheDir().toString());
            if (!file.exists()) {//判断目录是否存在
                file.mkdir();
            }
            f = new File(file, bitName + ".jpg");
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fOut != null) {
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f.toString();
    }
    /**
     * 判断是否有sd卡
     *
     * @return
     */
    public static boolean isStorage() {
        boolean isstorage = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        return isstorage;
    }

    /**
     *把Bimtmap转成Base64，用于上传图片到服务器，一般是先压缩然后转成Base64，在上传
     *
     *
     */
    public static String getBitmapStrBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 把Base64转换成Bitmap
    public static Bitmap getBitmapFromBase64(String iconBase64) {
        byte[] bitmapArray = Base64.decode(iconBase64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
    }
}
