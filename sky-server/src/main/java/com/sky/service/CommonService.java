package com.sky.service;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {

    //上传文件
    String upload(MultipartFile file);
}
