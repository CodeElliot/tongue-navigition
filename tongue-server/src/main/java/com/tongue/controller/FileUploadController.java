package com.tongue.controller;

import com.tongue.constant.MessageConstant;
import com.tongue.result.Result;
import com.tongue.utils.AliyunOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 阿里云oss图片上传服务
 */
@RestController
@Slf4j
public class FileUploadController {
    @Autowired
    private AliyunOssUtil aliyunOssUtil;
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception{
        log.info("上传图片到oss...");
//        获取原文件名
        String originalFilename = file.getOriginalFilename();
        //保证文件名是唯一的
        String fileName=UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        String url = aliyunOssUtil.upload(fileName, file.getInputStream());
        return Result.success(MessageConstant.FILE_UPLOAD_SUCCESS,url);
    }
}
