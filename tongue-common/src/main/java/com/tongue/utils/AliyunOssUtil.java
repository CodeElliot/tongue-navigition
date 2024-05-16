package com.tongue.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.tongue.properties.AliyunOssProperty;
import com.tongue.properties.JwtProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 阿里云oss服务工具
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AliyunOssUtil {
    private String endpoint;
    private String bucketName;
    final static String ossAccessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
    final static String ossAccessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");

    public String upload(String objectName, InputStream inputStream) throws Exception {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, ossAccessKeyId, ossAccessKeySecret);
        String url = "";
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            url = "https://" + bucketName +"."+ endpoint + "/" + objectName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}
