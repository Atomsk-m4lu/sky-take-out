package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.CommonService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/admin/common")
@RestController
@Slf4j
@Api(tags = "通用接口")
public class CommonController {

    @Autowired
    private CommonService commonService;

    //上传文件
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("上传文件：{}", file);
        return Result.success(commonService.upload(file));


    }

}
