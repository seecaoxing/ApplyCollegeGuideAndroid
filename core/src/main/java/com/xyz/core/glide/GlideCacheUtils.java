package com.xyz.core.glide;

import android.os.Environment;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

import com.bumptech.glide.Glide;
import com.xyz.core.CoreApplication;
import com.xyz.core.log.NTLog;
import com.xyz.core.util.SystemUtils;

import java.io.File;
import java.io.Serializable;

/**
 * glide 控制类
 */
public class GlideCacheUtils {

    private static final String TAG = "GlideCacheUtils";

    public static final String DISK_CACHE_NAME = "bitmap_glide";
    public static final int MAX_DISK_CACHE_SIZE = 100 * 1024 * 1024;
    public static final int MIN_DISK_CACHE_SIZE = 20 * 1024 * 1024;
    public static final int MAX_MEMORY_CACHE_SIZE = 8 * 1024 * 1024;
    public static final int MAX_BITMAP_POOL_SIZE = 8 * 1024 * 1024;

    private static DiskCacheInfo diskCacheInfo;

    /**
     * @Name clearMemoryCache
     * @Description 清理Glide内存缓存，必须在UI线程中调用
     */
    @UiThread
    public static void clearMemoryCache() {
        Glide.get(CoreApplication.getInstance()).clearMemory();
    }

    /**
     * @Name clearDiskCache
     * @Description 必须在后台线程中调用，建议同时clearMemory()
     */
    @WorkerThread
    public static void clearDiskCache() {
        Glide.get(CoreApplication.getInstance()).clearDiskCache();
    }

    public static void trimMemory(int level) {
        Glide.get(CoreApplication.getInstance()).trimMemory(level);
    }

    private static void ensureDir(File file) {
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
    }

    public static DiskCacheInfo ensureDiskCacheFolder() {
        if (diskCacheInfo != null) {
            return diskCacheInfo;
        }
        File bitmapCacheDir;
        long dataCacheSize = SystemUtils.getRomAvailableSize();
        //data目录剩余空间过小，并且sd卡状态可用时才放到sd上，否则放在data目录下
        if (dataCacheSize < MIN_DISK_CACHE_SIZE * 2 &&
                (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !Environment.isExternalStorageRemovable())) {
            File externalCacheDir = CoreApplication.getInstance().getExternalCacheDir();
            if (externalCacheDir != null && externalCacheDir.exists() && externalCacheDir.canWrite() && externalCacheDir.canRead()) {
                bitmapCacheDir = new File(externalCacheDir, DISK_CACHE_NAME);
                ensureDir(bitmapCacheDir);
                if (bitmapCacheDir.exists()) {
                    diskCacheInfo = new DiskCacheInfo(bitmapCacheDir.getAbsolutePath(), (int) Math.min(MAX_DISK_CACHE_SIZE, (long) (SystemUtils.getSDAvailableSize() * 0.5)));
                }
            }
        }
        if (diskCacheInfo == null || diskCacheInfo.cacheSize < MIN_DISK_CACHE_SIZE) {
            bitmapCacheDir = new File(CoreApplication.getInstance().getCacheDir(), DISK_CACHE_NAME);
            ensureDir(bitmapCacheDir);
            diskCacheInfo = new DiskCacheInfo(bitmapCacheDir.getAbsolutePath(), (int) Math.min(MAX_DISK_CACHE_SIZE, Math.max((long) (dataCacheSize * 0.5), MIN_DISK_CACHE_SIZE)));
        }

        NTLog.i(TAG, "ensureDiskCache getExternalStorageDirectory:" + Environment.getExternalStorageDirectory() + ";getExternalCacheDir:" +
                CoreApplication.getInstance().getExternalCacheDir());
        NTLog.i(TAG, "ensureDiskCache:" + diskCacheInfo);
        return diskCacheInfo;
    }

    public static class DiskCacheInfo implements Serializable {
        private int cacheSize;
        private String diskCacheFolder;

        public DiskCacheInfo(String diskCacheFolder, int cacheSize) {
            this.diskCacheFolder = diskCacheFolder;
            this.cacheSize = cacheSize;
        }

        public int getCacheSize() {
            return cacheSize;
        }

        public String getDiskCacheFolder() {
            return diskCacheFolder;
        }

        @Override
        public String toString() {
            return "DiskCacheInfo{" +
                    "diskCacheFolder='" + diskCacheFolder + '\'' +
                    ", cacheSize=" + cacheSize +
                    '}';
        }
    }
}
