package com.qf.utils;

import java.io.File;
import java.util.HashMap;

/**
 * @author zhaojian
 */
public class DownLoadUtils {

    /**
     *
     * @param file
     * @param filenames
     */
    public static void getFileList(File file, HashMap<String,String> filenames){
        //获取当前文件对象下的所有内容（文件、文件夹）
        File[] files = file.listFiles();
        //如果数组不为空，证明有文件、文件夹
        if(files!=null){
            //每次拿到文件对象（文件、文件夹   ）
            for (File file1 : files) {
                if(file1.isDirectory()){
                    getFileList(file1,filenames);
                }else{
                    //获得文件的名称
                    String filename = file1.getName();
                    //获取第一个_的下标
                    int i = filename.indexOf("_");
                    //获取源文件名称（可能包含_）
                    String realName= filename.substring(i + 1);
                    //UUID键   源文件名 值
                    filenames.put(filename, realName);
                }
            }
        }
    }
}
