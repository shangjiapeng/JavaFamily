package com.shang.demo;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;


/**
 * @Author: 尚家朋
 * @Date: 2019-06-28 14:39
 * @Version 1.0
 */
public class PictureToNumber {
    public static void main(String[] args) {
        createAsciiPic("/Users/shangjiapeng/idea-projects/mybatisplusdemo/src/main/resources/a.png");
    }
    /**
     * @param path
     * 图片路径
     */
    public static void createAsciiPic(final String path) {
//        final String base = "*{}[]()|!;:^'`.";// 字符串由复杂到简单
        final String base = "!;:'`.";// 字符串由复杂到简单
        try {
            String result = "";
            final BufferedImage image = ImageIO.read(new File(path));
            for (int y = 0; y < image.getHeight(); y += 6) {
                for (int x = 0; x < image.getWidth(); x+=3) {
                    final int pixel = image.getRGB(x, y);
                    final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
                    final float gray = 0.299f * r + 0.578f * g + 0.114f * b;
                    final int index = Math.round(gray * (base.length() + 1) / 255);
                    result += index >= base.length() ? " " : String.valueOf(base.charAt(index));
                    System.out.print(index >= base.length() ? " " : String.valueOf(base.charAt(index)));
                }
                System.out.println();
                result += "\r\n";
            }
            writeTxtFile(result,"/Users/shangjiapeng/idea-projects/mybatisplusdemo/src/main/resources/photo.txt");
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 字符保存到txt文件中
     *
     * @param imageStr 字符
     * @param txtPath  txt文件
     * @return boolean
     * @throws Exception
     */
    private static boolean writeTxtFile(String imageStr, String txtPath) throws Exception {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        String filein = imageStr;
        String temp = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 文件路径
            File file = new File(txtPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();
            // 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
            }
            buf.append(filein);
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e) {
            System.out.println("文件保存失败" + e.getMessage());
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return flag;
    }
}

