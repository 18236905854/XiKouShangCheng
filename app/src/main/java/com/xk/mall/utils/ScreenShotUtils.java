package com.xk.mall.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 截图工具类
 */
public class ScreenShotUtils {

    /**
     * 将 Bitmap 保存到SD卡
     * @param context
     * @param mybitmap
     * @param name
     * @return
     */
    public static boolean saveBitmapToSdCard(Context context, Bitmap mybitmap, String name){
        boolean result = false;
        //创建位图保存目录
        String path = Environment.getExternalStorageDirectory() + "/xikou/";
        File sd = new File(path);
        if (!sd.exists()){
            sd.mkdir();
        }

        File file = new File(path+name+".jpg");
        if(file.exists()){
            file.delete();
        }
        FileOutputStream fileOutputStream = null;
        if (!file.exists()){
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    fileOutputStream = new FileOutputStream(file);
                    mybitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();

                    //update gallery
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(file);
                    intent.setData(uri);
                    context.sendBroadcast(intent);
//                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                    result = true;
                }
                else{
                    Toast.makeText(context, "不能读取到SD卡", Toast.LENGTH_SHORT).show();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            result=true;
        }
        return result;
    }


    /**
     * 获取一个 View 的缓存视图
     *  (前提是这个View已经渲染完成显示在页面上)
     * @param view
     * @return
     */
    public static Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

}
