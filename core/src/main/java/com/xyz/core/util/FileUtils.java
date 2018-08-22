package com.xyz.core.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jason on 2017/11/8.
 */

public class FileUtils {
    /**
     * 往SD卡写入文件的方法
     *
     * @param context
     * @param rootDir
     * @param filename
     * @param bytes
     * @throws Exception
     */
    public static void saveFileToSD(Context context,
                                    String rootDir,
                                    String filename,
                                    byte[] bytes) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + File.separatorChar + rootDir;
            File dir1 = new File(filePath);
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
            filename = filePath + File.separatorChar + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);
            //将bytes写入到输出流中
            output.close();
            //关闭输出流
            ToastUtils.showMessage(context, "保存成功", Toast.LENGTH_SHORT);
        } else {
            ToastUtils.showMessage(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT);
        }

        SystemUtils.insertGallery(context, filename);
    }

    public static void copyFile(File sourcefile, File targetFile) {
        FileInputStream input = null;
        BufferedInputStream inbuff = null;
        FileOutputStream out = null;
        BufferedOutputStream outbuff = null;

        try {

            input = new FileInputStream(sourcefile);
            inbuff = new BufferedInputStream(input);

            out = new FileOutputStream(targetFile);
            outbuff = new BufferedOutputStream(out);

            byte[] b = new byte[1024 * 5];
            int len = 0;
            while ((len = inbuff.read(b)) != -1) {
                outbuff.write(b, 0, len);
            }

            outbuff.flush();
        } catch (Exception ex) {

        } finally {
            try {

                if (inbuff != null)
                    inbuff.close();
                if (outbuff != null)
                    outbuff.close();
                if (out != null)
                    out.close();
                if (input != null)
                    input.close();
            } catch (Exception ex) {

            }
        }
    }

    /**
     * 根据文件路径获取文件
     */
    public static File getFile(final String filePath) {
        return filePath == null ? null : new File(filePath);
    }

    /**
     * 判断文件是否存在
     */
    public static boolean isFileExists(final String filePath) {
        return isFileExists(getFile(filePath));
    }

    /**
     * 判断文件是否存在
     */
    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 将src1 & src2的内容合并到dest文件内
     */
    public static void mergerFile(File src1, File src2, File dest) throws IOException {

        if (dest.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dest.delete();
        }
        FileOutputStream out = new FileOutputStream(dest);
        byte[] temp = new byte[1024 * 10];

        if (src1 != null && src1.exists()) {
            writeData(src1, out, temp);
        }

        if (src2 != null && src2.exists()) {
            writeData(src2, out, temp);
        }

        out.close();
    }

    private static void writeData(File src2, FileOutputStream out, byte[] temp) throws IOException {
        long total;
        FileInputStream in;
        total = src2.length();
        in = new FileInputStream(src2);
        long count = 0;
        while (count < total) {
            int size = in.read(temp);
            if (size != -1) {
                out.write(temp, 0, size);
                count += size;
            }
        }
        in.close();
    }

    /**
     * Delete the file tree.
     *
     * @param file
     * @return true if file does not exist or delete successfully. false if there is an Exception during operation or sdcard is not mounted.
     */
    public static boolean deleteFile(File file) {
//		if(SystemUtils.isSDCardMounted()) {
        if (!file.exists()) {
            return true;
        }
        try {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
//		} else return false;
    }
}
