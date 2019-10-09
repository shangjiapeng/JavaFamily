package com.shang.demo.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 腾讯云对象存储
 */
public class CosUtils {

    // 1 初始化用户身份信息(secretId, secretKey，可在腾讯云后台中的API密钥管理中查看！
    private static COSCredentials cred = new BasicCOSCredentials("AKIDZ0HQOBjzlMn7GNEFxVt8FJGKLEwT4367", "9C782nOOK6lHcRCnEVPCrLemkW7ar4Ll");

    // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224，根据自己创建的存储桶选择地区
    private static ClientConfig clientConfig = new ClientConfig(new Region("ap-chongqing"));

    // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式，这个为存储桶名称
    private static String bucketName = "qishimai-0-test-1257428522";


    /**
     * 上传文件, 根据文件大小自动选择简单上传或者分块上传。
     *
     * @param localFile 文件
     * @param fileName 文件名
     * @param pathFile 文件路径
     */

    public static void uploadOneFile(String pathFile,File localFile,String fileName) {

        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        ExecutorService threadPool = Executors.newFixedThreadPool(32);

        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosclient, threadPool);

        String key = pathFile + fileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        try {
            // 返回一个异步结果Upload, 可同步的调用waitForUploadResult等待upload结束, 成功返回UploadResult, 失败抛出异常.
            long startTime = System.currentTimeMillis();
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            long endTime = System.currentTimeMillis();
            // System.out.println("used time: " + (endTime - startTime) / 1000);
            // System.out.println(uploadResult.getETag());
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        transferManager.shutdownNow();
        cosclient.shutdown();
    }

}


