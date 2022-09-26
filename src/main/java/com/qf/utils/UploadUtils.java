package com.qf.utils;

import java.io.File;
import java.util.UUID;

/**
 * @author zhaojian
 */
public class UploadUtils {


    /**
     * 文件重命名, 防止文件重名
     * @param fileName
     * @return
     */
    public static String newFileName(String fileName) {
        String newName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + fileName;
        return newName;
    }

    /**
     * 散列算法规划文件存放目录, 防止一个文件夹文件太多
     * @param basePath
     * @param fileName
     * @return
     */
    public static String newFilePath(String basePath, String fileName) {
        //获取文件hashCode
        int hashCode = fileName.hashCode();
        //hashCode和15与运算, 得出0-15二级目录
        int path1 = hashCode & 15;
        //hashCode向右位移4位和15与运算, 得出0-15三级目录
        int path2 = (hashCode >> 4) & 15;
        //完整文件存放目录
        String dirPath = basePath + File.separator + path1 + File.separator + path2;

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    public static void main(String[] args) {
        String s = newFilePath("D:\\ideaWorkspace\\workspace3\\out\\artifacts\\fileupload_war_exploded\\WEB-INF" +
                "\\upload", "201869231cc245428c86b99d01083b77_0-3.jpg");
        System.out.println(s);
    }
}

