
package com.wh.dm.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    public static String getCacheSize() {

        String sdcardDir = Environment.getDataDirectory().getPath();
        String database = "/data/com.wh.dm/files";
        String cache = "/data/com.wh.dm/cache/webviewCacheChromium";
        long size = 0;
        File file = new File(sdcardDir + database);
        File file2 = new File(sdcardDir + cache);
        try {
            size = getFilesSize(file) + getFilesSize(file2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FormetFileSize(size);
    }

    /*** 获取文件大小 ***/
    public static long getFileSize(File f) throws Exception {

        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
        } else {
            f.createNewFile();
            System.out.println("文件不存在");
        }
        return s;
    }

    /*** 获取文件夹大小 ***/
    public static long getFilesSize(File f) throws Exception {

        long size = 0;
        File flist[] = f.listFiles();
        if (null == flist) {
            return 0;
        }
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFilesSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /*** 转换文件大小单位(b/mb/gb) ***/
    public static String FormetFileSize(long files) {// 转换文件大小

        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString = "";
        if (files < 1024) {
            fileSizeString = df.format((double) files) + "B";
        } else if (files < 1048576) {
            fileSizeString = df.format((double) files / 1024) + "K";
        } else if (files < 1073741824) {
            fileSizeString = df.format((double) files / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) files / 1073741824) + "G";
        }
        return fileSizeString;
    }

    // delete cache
    public static void deleteCache() {

        // delete files
        String sdcardDir = Environment.getDataDirectory().getPath();
        String database = "/data/com.wh.dm/files";
        File file = new File(sdcardDir + database);
        file.delete();
        File[] fl = file.listFiles();
        if (fl != null) {
            for (int i = 0; i < fl.length; i++) {
                fl[i].delete();
            }
        }
        // delete cache
        String cache = "/data/com.wh.dm/cache/webviewCacheChromium";
        File cfile = new File(sdcardDir + cache);
        cfile.delete();
        File[] f2 = cfile.listFiles();
        if (f2 != null) {
            for (int i = 0; i < f2.length; i++) {
                f2[i].delete();
            }
        }

    }

    // save a file
    public static boolean savePicture(Bitmap bitmap) {

        // 以下方法也可以保存图片
        String sdcardDir = Environment.getExternalStorageDirectory().getPath() + "/meike";
        String filename = getCurrentTime("yyyy-MM-dd-hh-mm-ss");
        String filePath = filePath = sdcardDir + "/" + filename + ".jpg";
        try {

            File dirFile = new File(sdcardDir); // 图片全路径
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
            File file = new File(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            // 压缩图片到指定的outputstream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            // 刷新此缓冲输出流
            bos.flush();
            bos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // get string of current time
    public static String getCurrentTime(String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    //
    /**
     * Get image from newwork
     * 
     * @param path The path of image
     * @return InputStream
     * @throws Exception
     */
    public static InputStream getImageStream(String path) throws Exception {

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return conn.getInputStream();
        }
        return null;
    }
}
