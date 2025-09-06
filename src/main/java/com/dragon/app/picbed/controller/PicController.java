package com.dragon.app.picbed.controller;

import com.dragon.app.picbed.common.Result;
import com.dragon.app.picbed.common.ResultUtils;
import com.dragon.app.picbed.controller.dio.PicInfoDio;
import com.dragon.app.picbed.exception.AuthenticationException;
import com.dragon.app.picbed.exception.ErrorCode;
import com.dragon.app.picbed.exception.ThrowUtils;
import com.dragon.app.picbed.service.FileUploadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class PicController {

    // 利用Environment获取application.yml配置
    public Environment environment;
    public FileUploadService fileUploadService;

    @PostMapping("/upload")
    public Result<PicInfoDio> upload(@RequestHeader(name = "Authorization") String token, MultipartFile file) {
        // token校验
        ThrowUtils.throwIf(
                !token.equals("Bearer " + environment.getProperty("token")),
                new AuthenticationException("token校验失败"));
        // 校验文件是否为空
        ThrowUtils.throwIf(
                ObjectUtils.isEmpty(file) || file.isEmpty(),
                new IllegalArgumentException("文件不能为空"));

        // 上传文件
        String url = fileUploadService.upload(file);



        return ResultUtils.success(new PicInfoDio(url));
    }
}
