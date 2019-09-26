package com.shang.demo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p>下载工具类</p>
 * 作用: 把一个url中的数据存储到指定文件的指定名称的文件中去
 * @Author: ShangJiaPeng
 * @Since: 2019-08-29 14:26
 */
public class DownLoadUtil {

    public static void download(String urlStr, String filename, String savePath) throws IOException {
        //创建url对象
        URL url = new URL(urlStr);
        //打开url
        URLConnection connection = url.openConnection();
        //设置请求超时时间--毫秒
        connection.setConnectTimeout(5000);
        //输入流
        InputStream inputStream = connection.getInputStream();
        //缓冲数据
        byte[] bytes = new byte[1024];
        //读取到的数据长度
        int lenth;
        //读写文件文件
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(file.getPath() + "\\" + filename);
            //先读取到缓存数据 bytes中
            while ((lenth=inputStream.read(bytes)) != -1) {//当没有读到文件的末尾就继续读数据
                //把bytes 数组写入文件
                fileOutputStream.write(bytes, 0, lenth);
            }
            fileOutputStream.close();
            inputStream.close();
        }
    }
}
