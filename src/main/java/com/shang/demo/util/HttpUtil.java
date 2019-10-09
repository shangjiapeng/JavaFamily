package com.shang.demo.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>http请求工具类</p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-08-14 16:32
 */
@Component
public class HttpUtil {

    /***
     * 模拟发送get请求
     *   注意:设置请求参数
     *   List<NameValuePair> params = new ArrayList<>();
     *   params.add(new BasicNameValuePair("key", "value"));
     * @param url  请求的地址
     * @param params 参数集合
     * @param timeout 超时时间(毫秒)
     */
    public static String sendGet(String url, List<NameValuePair> params, int timeout) {

        // 获取httpclient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String paramStr = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params, Charset.forName("UTF-8")));
            //拼接参数
            StringBuffer sb = new StringBuffer();
            sb.append(url);
            sb.append("?");
            sb.append(paramStr);
            //创建get请求
            HttpGet httpGet = new HttpGet(sb.toString());
            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
            httpGet.setConfig(requestConfig);
            // 提交参数发送请求
            response = httpclient.execute(httpGet);

            // 得到响应信息
            int statusCode = response.getStatusLine().getStatusCode();
            // 判断响应信息是否正确
            if (statusCode != HttpStatus.SC_OK) {
                // 终止并抛出异常
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭所有资源连接
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String sendGet(String url,int timeout) {
        return sendGet(url, null,timeout);
    }

    /***
     * 模拟发送post请求(测试)
     * @param url  请求的地址
     * @param params 参数集合
     * @param timeout 超时时间
     */
    public static String sendPost(String url, List<NameValuePair> params, int timeout) {

        // 获取httpclient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String paramStr = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            // 创建post请求
            HttpPost httpPost = new HttpPost(url);
            // 设置POST参数
            UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(params, Charset.forName("UTF-8"));
            httpPost.setEntity(postEntity);
            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
            httpPost.setConfig(requestConfig);
            // 提交参数发送请求
            response = httpclient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
            // 得到响应信息
            int statusCode = response.getStatusLine().getStatusCode();
            // 判断响应信息是否正确
            if (statusCode != HttpStatus.SC_OK) {
                // 终止并抛出异常
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭所有资源连接
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String sendPost(String url,int timeout) {
        return sendPost(url, null,timeout);
    }

    /**
     * 用于把map参数转换成NameValuePair
     * @param params
     * @return
     */
    private List<NameValuePair> map2NameValuePairList(Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if(params.get(key) != null) {
                    String value = String.valueOf(params.get(key));
                    list.add(new BasicNameValuePair(key, value));
                }
            }
            return list;
        }
        return null;
    }

}
