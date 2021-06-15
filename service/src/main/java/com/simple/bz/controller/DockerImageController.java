package com.simple.bz.controller;

import com.simple.bz.dto.DockerBuildGitRequest;
import com.simple.bz.dto.DockerBuildRequest;
import com.simple.bz.dto.DockerImageDto;
import com.simple.bz.service.DockerImageService;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
import com.simple.common.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dockerimage")
public class DockerImageController extends BaseController {

    private final DockerImageService service;


    @GetMapping(path = "/findAll")
    GenericResponse findAll() {

        List<DockerImageDto> dtoList = service.findAll();
        if (null == dtoList) {
            dtoList = new ArrayList<DockerImageDto>();
        }
        GenericResponse result = GenericResponse.build().addKey$Value("list", dtoList);

        return result;
    }

    @GetMapping(path = "/findById")
    GenericResponse findById(@RequestParam("id") Long applicationId) {

        System.out.println("applicationId:" + String.valueOf(applicationId));

        DockerImageDto application = service.findById(applicationId);
        GenericResponse result = GenericResponse.build().setDataObject(application);
        return result;
    }

    @PostMapping(path = "/save")
    GenericResponse createDeployment(@RequestBody GenericRequest req) {

        DockerImageDto dto = req.getObject(DockerImageDto.class);
        System.out.println(dto.toString());
        service.save(dto);
        GenericResponse result = new GenericResponse(dto);
        return result;
    }

    @PostMapping(path = "/buildImage")
    GenericResponse buildDockerImage(@RequestBody GenericRequest req) {
        DockerBuildRequest dto = req.getObject(DockerBuildRequest.class);
        System.out.println(dto.toString());
        //service.save(dto);
        service.buildDockerImage(dto);
        GenericResponse result = new GenericResponse(dto);
        return result;
    }

    @PostMapping(path = "/gitAutoDeployment")
    GenericResponse buildDockerImage(DockerBuildRequest request, @RequestBody DockerBuildGitRequest gitRequest) {
        String commitMessage = null;
        if ((null != gitRequest) && (null != gitRequest.getHead_commit())) {
            commitMessage = gitRequest.getHead_commit().getMessage();
            System.out.println("Message ===>" + commitMessage + "Current Head Commit Message of this Git Push ====>" + gitRequest.getHead_commit().toString());

        } else {
            GenericResponse result = new GenericResponse(request);
            return result;
        }

        boolean needDeployment = StringUtils.contains(gitRequest.getHead_commit().getMessage(), "release");

        if (needDeployment) {


            String[] params = StringUtils.substringsBetween(commitMessage, "[", "]");
            if ((null != params) && (params.length >= 1)){
                String targetEnvType = params[0];
                System.out.println("targetEnvType ==>" + targetEnvType);
                if (StringUtils.isNotBlank(targetEnvType)) {
                    request.setEnvType(targetEnvType);
                }
            }
            if ((null != params) && (params.length >= 2)){
                String applicationName = params[1];
                System.out.println("applicationName ==>" + applicationName);
                if (StringUtils.isNotBlank(applicationName)) {
                    request.setApplicationName(applicationName);
                }
            }



            System.out.println("Final request =====>" + request.toString());
            if(StringUtils.isBlank(request.getEnvType()) || request.getEnvType().toLowerCase().equals("image")){
                service.buildDockerImage(request);
            }else if(request.getEnvType().toLowerCase().equals("uat") || request.getEnvType().toLowerCase().equals("prod")){
                service.buildDockerImageAndDeploy(request);

            }else{
                System.out.println("No deployment need to do!");
            }

        } else {
            System.out.println("No deployment requirement!");
        }
        GenericResponse result = new GenericResponse(request);
        return result;
    }

    @GetMapping(path = "/findByApplicationId")
    GenericResponse getByApplicationId(@RequestParam("id") Long applicationId) {

        System.out.println("projectId:" + String.valueOf(applicationId));

        List<DockerImageDto> applications = service.findByApplicationId(applicationId);
        GenericResponse result = GenericResponse.build().addKey$Value("list", applications);
        return result;
    }


    @PostMapping(path = "/update/{id}")
    public GenericResponse updateSave(@RequestBody GenericRequest req, @PathVariable Long id) {
        DockerImageDto dto = req.getObject(DockerImageDto.class);
        dto.setId(id);
        System.out.println("applicationId:" + String.valueOf(id));
        service.update(dto);
        GenericResponse result = new GenericResponse(dto);
        return result;


    }


    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public Long removeById(@PathVariable Long id) {
        service.remove(id);
        return id;
    }


}
