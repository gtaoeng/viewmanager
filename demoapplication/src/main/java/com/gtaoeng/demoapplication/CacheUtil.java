package com.gtaoeng.demoapplication;

import android.content.Context;
import android.os.Environment;
import android.text.format.DateFormat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by gtaoeng on 2017/11/3.
 */

public class CacheUtil {

    public static Map<String, String> downloadMap = new HashMap<>();
    public static String CacheFolder = "lama-hand";
    public static String CachePath = null;

    public static String getCachePath() {
        if (CachePath != null)
            return CachePath;
        CachePath = Environment.getExternalStorageDirectory() + "/"
                + CacheFolder;
        File f = new File(CachePath);
        if (!f.exists()) {
            f.mkdir();
        }
        return CachePath;
    }

    public static String getImageCache() {

        String mapCache = getCachePath();
        String temp = mapCache + "/ImageCache";
        File f = new File(temp);
        if (!f.exists()) {
            f.mkdirs();
        }
        return temp;
    }

    public static String getCaremaCache() {

        String mapCache = getCachePath();
        String temp = mapCache + "/CaremaCache";
        File f = new File(temp);
        if (!f.exists()) {
            f.mkdirs();
        }
        return temp;
    }

    public static String getArcgisMapFile(String fileName) {
        String filepath = getCachePath() + "/" + fileName;
        String temp = "file://" + filepath;
        File f = new File(filepath);
        if (!f.exists()) {
            return null;
        }
        return temp;
    }

    public static String getCurDate() {

        String time = DateFormat.format("yyyy-MM-dd kk:mm:ss",
                Calendar.getInstance(Locale.CHINA))
                + "";
        return time;
    }

    public static String getCurDateName() {

        String time = DateFormat.format("yyyyMMddkkmmss",
                Calendar.getInstance(Locale.CHINA))
                + "";
        return time;
    }

    public static String readJson(Context context, String fileName) {
        try {
            String filePath = getCachePath() + "/" + fileName;
            byte[] buffer = null;
            if (new File(filePath).exists()) {
                buffer = ReadFile(filePath);
            } else {
                buffer = readAssetsTxt(context, fileName);
                WriteFile(filePath, buffer);
            }
            String text = new String(buffer, "utf-8");
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(text);
            text = m.replaceAll("");

            return text;
        } catch (Exception ee) {

        }
        return "";
    }

    public static byte[] readAssetsTxt(Context context, String fileName) {
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean moveDirectory(String srcDirName, String destDirName) {

        File srcDir = new File(srcDirName);
        if (!srcDir.exists() || !srcDir.isDirectory())
            return false;

        File destDir = new File(destDirName);
        if (!destDir.exists()) {
            boolean bc = destDir.mkdirs();
            if (!bc) destDir.mkdirs();
        }

        File[] sourceFiles = srcDir.listFiles();
        for (File sourceFile : sourceFiles) {
            if (sourceFile.isFile())
                moveFile(sourceFile.getAbsolutePath(),
                        destDir.getAbsolutePath());
            else if (sourceFile.isDirectory())
                moveDirectory(
                        sourceFile.getAbsolutePath(),
                        destDir.getAbsolutePath() + File.separator
                                + sourceFile.getName());
        }
        return srcDir.delete();
    }


    private static boolean moveFile(String srcFileName, String destDirName) {

        File srcFile = new File(srcFileName);
        if (!srcFile.exists() || !srcFile.isFile())
            return false;

        File destDir = new File(destDirName);
        if (!destDir.exists())
            destDir.mkdirs();

        return srcFile.renameTo(new File(destDirName + File.separator
                + srcFile.getName()));
    }

    public static void deleteFile(String folder) {
        File file = new File(folder);
        if (file.isFile()) {
            file.delete();
            return;
        } else if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                File subFile = childFiles[i];
                if (subFile.isDirectory()) {
                    deleteFile(subFile.getPath());
                } else {
                    childFiles[i].delete();
                }
            }
            file.delete();
        }
    }


    public static double calcLength(double lon1, double lat1, double lon2,
                                    double lat2) {
        return Math.sqrt((lon1 - lon2) * (lon1 - lon2) + (lat1 - lat2)
                * (lat1 - lat2));
    }


    public static boolean WriteFile(String fileName, byte[] datas) {
        String todbfile = fileName;
        try {
            RandomAccessFile rf = new RandomAccessFile(todbfile, "rw");
            long len = rf.length();
            rf.seek(len);
            rf.write(datas);
            rf.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int ReadFileLength(String fileName) {
        RandomAccessFile rf;
        try {
            rf = new RandomAccessFile(fileName, "rw");
            int len = (int) rf.length();
            rf.close();
            return len;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }

    }

    public static byte[] ReadFile(String fileName, int offset) {
        String todbfile = fileName;
        try {
            RandomAccessFile rf = new RandomAccessFile(todbfile, "rw");
            int len = (int) rf.length();
            rf.seek(offset);
            int fileSize = 1024 * 20;
            byte[] bys = null;
            if (offset + fileSize <= len) {
                bys = new byte[fileSize];
                rf.read(bys, 0, fileSize);
            } else {
                int sz = len - offset;
                bys = new byte[sz];
                rf.read(bys, 0, sz);
            }

            rf.close();
            return bys;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] ReadFile(String fileName) {
        String todbfile = fileName;
        try {
            RandomAccessFile rf = new RandomAccessFile(todbfile, "rw");
            int len = (int) rf.length();
            int offset = 0;
            rf.seek(0);
            int fileSize = 1024;
            byte[] bys = new byte[len];
            while (true) {
                if (offset + fileSize <= len) {
                    offset += rf.read(bys, offset, fileSize);
                } else {
                    int sz = len - offset;
                    rf.read(bys, offset, sz);
                    break;
                }
            }

            rf.close();
            return bys;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @param file
     * @return byte单位
     */
    public static long getFolderSize(File file) {

        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return size;
    }

    /**
     * @param file 文件夹
     * @return 返回文件夹中的文件数量
     */
    public static long getFolderCount(File file) {

        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderCount(fileList[i]);

                } else {
                    size = size + 1;

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return size;
    }

    public static String saveFile(String fileName, byte[] buffer) {
        FileOutputStream fos = null;
        try {
            String filePath = getCachePath() + "/" + fileName;
            File file = new File(filePath);
            // 先清空内容再写入
            fos = new FileOutputStream(file);
            fos.write(buffer);
            fos.close();
            return filePath;
        } catch (Exception ex) {

            ex.printStackTrace();

            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }
}
