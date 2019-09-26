package com.shang.demo.util;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

/**
 * <p>分词工具类</p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-08-30 15:14
 */
public class IKUtil {

    /**
     *
     * @param content 待分析的字符串
     * @param splitChar 分出来的词汇 用splitChar分开(比如用空格隔开)
     *      IKSegmenter的第一个构造参数为StringReader类型,StringReader是装饰Reader的类，其用法是读取一个String字符串
     *      IKSegmenter 的第二个构造参数userSmart 为切分粒度 true表示最大切分 false表示最细切分
     *      Lexeme: 词单位类
     * @return
     * @throws IOException
     */
    public static String split(String content ,String splitChar) throws IOException{

        StringReader stringReader=new StringReader(content);
        IKSegmenter ikSegmenter = new IKSegmenter(stringReader, true);  //true表示最大切分 false表示最细切分
        Lexeme lex =null;
        StringBuilder stringBuilder = new StringBuilder("");
        while ((lex=ikSegmenter.next())!=null){
            stringBuilder.append(lex.getLexemeText()+splitChar); //拼接字符串
        }
        return stringBuilder.toString();
    }
}
