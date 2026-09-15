package com.sky.service.impl;

import com.sky.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    //上传文件
    @Override
    public String upload(MultipartFile file) {

    }
}
