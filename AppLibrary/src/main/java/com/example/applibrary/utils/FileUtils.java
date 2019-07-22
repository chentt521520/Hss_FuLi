package com.example.applibrary.utils;

import java.io.File;

//文件处理
public class FileUtils {

    //删除文件
    public static boolean delAllFile(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return true;
            }
            if (!file.isDirectory()) {
                return true;
            }
            String[] tempList = file.list();
            File temp = null;
            for (int i = 0; i < tempList.length; i++) {
                if (path.endsWith(File.separator)) {
                    temp = new File(path + tempList[i]);
                } else {
                    temp = new File(path + File.separator + tempList[i]);
                }
                if (temp.isFile()) {
                    temp.delete();
                }
                if (temp.isDirectory()) {
                    delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                    delFolder(path + "/" + tempList[i]);
                }
            }
        } catch (Exception e) {
            return true;
        }
        return true;
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
