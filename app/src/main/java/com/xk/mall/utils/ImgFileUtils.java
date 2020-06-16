package com.xk.mall.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName: ImgFileUtils
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/12/6/006 18:56
 * @Version: 1.0
 */
public class ImgFileUtils {

    /** * 生成文件夹路径 */
    public static String SDPATH = Environment.getExternalStorageDirectory() + "/xikou/";

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "/xikou/");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新 //
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
    }

    /**
     * 将图片压缩保存到文件夹 *
     * @param bm
     * @param picName
     */
    public static void saveBitmap(Context context,Bitmap bm, String picName) {
        try {
            // 如果没有文件夹就创建一个程序文件夹
            if (!isFileExist("")) {
                File tempf = createSDDir("");
            } File f = new File(SDPATH, picName + ".JPEG");
            Log.e("filepath",f.getAbsolutePath());
            PosterXQImgCache.getInstance().setImgCache(f.getAbsolutePath());//缓存保存后的图片路径
            // 如果该文件夹中有同名的文件，就先删除掉原文件
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.e("imgfile", "已经保存");
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(f);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 质量压缩 并返回Bitmap *
     * @param image
     * 要压缩的图片
     * @return 压缩后的图片
     */
    private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 100) {
            // 重置baos即清空baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        // 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 质量压缩 
     *
     * @param bitmap
     * @param picName
     */
    public static void compressImageByQuality(final Bitmap bitmap, String picName) {
        // 如果没有文件夹就创建一个程序文件夹
        if (!isFileExist("")) {
            try {
                File tempf = createSDDir("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File f = new File(SDPATH, picName + ".JPEG");
        // 如果该文件夹中有同名的文件，就先删除掉原文件
        if (f.exists()) {
            f.delete();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 500) {
        // 重置baos即让下一次的写入覆盖之前的内容 baos.reset();
        // 图片质量每次减少5 options -= 5;
        // 如果图片质量小于10，则将图片的质量压缩到最小值
            if (options < 0) options = 0;
        // 将压缩后的图片保存到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        // 如果图片的质量已降到最低则，不再进行压缩
            if (options == 0) break;
        }
        // 将压缩后的图片保存的本地上指定路径中
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(new File(SDPATH, picName + ".JPEG"));
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建文件夹 *
     * @param dirName
     * 文件夹名称 
     * @return 文件夹路径
     * @throws IOException
     */
    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)) {
            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        } return dir;
    }
    /**
     * 判断改文件是否是一个标准文件 *
     * @param fileName
     * 判断的文件路径 
     * @return 判断结果
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        return file.exists();
    }
    /**
     * 删除指定文件 *
     * @param fileName
     */
    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }
    /**
     * 删除指定文件 
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
        // 判断文件是否存在
            if (file.isFile()) {
                // 判断是否是文件
                file.delete();
                // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) {
                // 否则如果它是一个目录
                File files[] = file.listFiles();
                // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {
                    // 遍历目录下所有的文件
                    deleteFile(files[i]);
                    // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
            Log.i("TAG", "文件不存在！");
        }
    }
    /**
     * 删除指定文件夹中的所有文件 
     */
    public static void deleteDir() {
        File dir = new File(SDPATH);
        if (dir == null || !dir.exists() || !dir.isDirectory()) return;
        for (File file : dir.listFiles()) {
            if (file.isFile()) file.delete();
            else if (file.isDirectory()) deleteDir();
        }
        dir.delete();
    }
    /**
     * 判断是否存在该文件 *
     * @param path * 文件路径 
     * @return
     */
    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}