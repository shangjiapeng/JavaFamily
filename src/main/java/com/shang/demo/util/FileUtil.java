package com.shang.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>文件工具类</p>
 * 用于文本文件的读 取，合并，以及获取某目录下的文本文件名称
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-08-30 16:18
 */
public class FileUtil {

    /**
     * 将多个文本文件合并为一个文本文件
     *
     * @param outFileName 输出文件的文件名
     * @param inFileNames 输入文件的文件名集合
     * @throws IOException
     */
    public static void merge(String outFileName, List<String> inFileNames) throws IOException {

        FileWriter fileWriter = new FileWriter(outFileName, false);
        for (String inFileName : inFileNames) {
            try {
                String text = readToString(inFileName);
                fileWriter.write(text);
                System.out.println(text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fileWriter.close();
    }

    /**
     * 查找某目录下的所有文件名称
     *
     * @param path
     * @return
     */
    public static List<String> getFiles(String path) {

        List<String> fileNames = new ArrayList<>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        if (tempList != null) {
            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isFile()) {//如果是文件
                    fileNames.add(tempList[i].toString());
                }
                if (tempList[i].isDirectory()) {//如果是文件夹
                    fileNames.addAll(Objects.requireNonNull(getFiles(tempList[i].toString())));
                }
            }
            return fileNames;
        }
        return null;
    }


    /**
     * 读取文本文件内容到字符串
     *
     * @param fileName  文件名称
     * @return
     */
    public static String readToString(String fileName) throws IOException {

        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream inputStream = new FileInputStream(file);
        int read = inputStream.read(filecontent);
        inputStream.close();
        return new String(filecontent, encoding);
    }
}
