package com.dragon.app.picbed.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Service
public class FileUploadService {

    @Autowired
    private Environment environment;

    public String upload(MultipartFile file) {
        // 获取文件MD5
        try {
            String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());
            log.info("文件MD5:{}",md5);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        String newFileName = originalFilename;
        String baseFilePath = environment.getProperty("uploadpath");
        String sepa = File.separator;
        String path = baseFilePath + sepa;

        // 确保路径存在
        createDirectory(path);

        File targetFile = new File(path + sepa + newFileName);
        try {
            //将上传文件写到服务器上指定的文件夹
            file.transferTo(targetFile);
            String saveUrl = environment.getProperty("baseurl") + "/" + newFileName;
            return saveUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**创建目录**/
    public void createDirectory(String directoryPath){
        File targetFile = new File(directoryPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
    }
}
