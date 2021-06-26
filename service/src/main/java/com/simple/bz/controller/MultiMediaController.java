package com.simple.bz.controller;

import com.simple.bz.service.MultimediaService;
import com.simple.common.api.GenericResponse;
import com.simple.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/media")
@Api(tags = "文件上传服务")
public class MultiMediaController extends BaseController {
    private final MultimediaService service;

    /**
     * 上传文件
     * @param group 文件分组
     * @param file 文件数据
     * @return
     */
    @ApiOperation(value="上传图片")
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
