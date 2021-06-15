package com.simple.common.image;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

public class MultimediaUtils {
    private UploadManager uploadManager;
    private Auth auth ;
    private String upToken;
    private String domainName = "images.koudaibook.com";

    public void init(String accessKey, String secretKey, String bucket,String domainName){
        this.domainName = domainName;
        Configuration cf = new Configuration(Zone.zone0());
        uploadManager = new UploadManager(cf);
        auth = Auth.create(accessKey, secretKey);
        upToken = auth.uploadToken(bucket);

    }
    private String fileName(String group , String originalFilename) {

        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
        return group +"/"+ newFileName;
    }
    public String uploadImage(MultipartFile file,String group){
        try {
            if(null == group){group = "public";}
            String key = fileName(group , file.getOriginalFilename());
            System.out.println("current filename ==>" +  key +"upToken==>" + upToken);
            Response response = uploadManager.put(file.getBytes(), key, upToken);
            if(response.isOK()) {
                System.out.println("current status ==> OK");
                return this.domainName + "/" + key;
            }else {
                System.out.println("current status ==> failure");
                System.out.println(response.toString());
                return null;
            }

        }catch(IOException e) {
            e.printStackTrace();
            return null;
        }


    }
}
