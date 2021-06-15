package com.simple.bz.service;

import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import com.simple.common.image.MultimediaUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;

@Service
public class MultimediaService {

    private UploadManager uploadManager;
    private Auth auth ;

    private String upToken;
    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.domainName}")
    private String domainName;

    private MultimediaUtils mediaUtils = new MultimediaUtils();

    @PostConstruct
    private void init() {
        mediaUtils.init(accessKey,secretKey,bucket,domainName);
    }
    /**
     * 上传文件
     * @param group 文件分组
     * @param file 文件数据
     * @return
     */
    public String uploadImage(String group, MultipartFile file) {
        if(file == null || file.getSize() <= 0) {
            return null;
        }
        try {
            String resultFilename = mediaUtils.uploadImage(file,group);
            return resultFilename;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}