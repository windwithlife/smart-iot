package com.simple.bz.controller;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.Configuration;

import com.qiniu.util.Auth;

import com.simple.bz.service.GatewayDeviceService;
import com.simple.bz.service.MultimediaService;
import com.simple.common.api.GenericResponse;
import com.simple.common.controller.BaseController;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/media")
public class MultiMediaController extends BaseController {
    private final MultimediaService service;

    /**
     * 上传文件
     * @param group 文件分组
     * @param file 文件数据
     * @return
     */
    @PostMapping("/uploadImage/{group}")
    public GenericResponse upload(@PathVariable("group") String group, @RequestParam("file") MultipartFile file) {

        String filename = service.uploadImage(group,file);
        if(null == filename){
            return GenericResponse.error("failed to upload image");
        }else{
            return GenericResponse.build().addKey$Value("url", filename);
        }

    }

    private String fileName(String group , String originalFilename) {

        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
        return group +"/"+ newFileName;
    }

}
