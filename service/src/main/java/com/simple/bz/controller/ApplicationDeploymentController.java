package com.simple.bz.controller;

import com.simple.bz.dto.ApplicationDeploymentDto;
import com.simple.bz.dto.ApplicationReleaseListRequest;
import com.simple.bz.dto.DockerBuildRequest;
import com.simple.bz.dto.ReleaseRequest;
import com.simple.bz.service.ApplicationDeploymentService;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
import com.simple.common.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applicationrelease")
public class ApplicationDeploymentController extends BaseController {

    private final ApplicationDeploymentService service;


    @GetMapping(path = "/findAll")
    GenericResponse createDeployment() {

        List<ApplicationDeploymentDto> dtoLst = service.findAll();
        GenericResponse result = GenericResponse.build().addKey$Value("list", dtoLst);

        return result;
    }

    @GetMapping(path = "/findById")
    GenericResponse findById(@RequestParam("id") Long applicationId) {

        System.out.println("applicationId:" + String.valueOf(applicationId));

        ApplicationDeploymentDto application = service.findById(applicationId);
        GenericResponse result = GenericResponse.build().setDataObject(application);
        return result;
    }

    @PostMapping(path = "/save")
    GenericResponse createDeployment(@RequestBody GenericRequest req) {

        ApplicationDeploymentDto dto = req.getObject(ApplicationDeploymentDto.class);
        System.out.println(dto.toString());
        service.save(dto);
        GenericResponse result = new GenericResponse(dto);
        return result;
    }

    @GetMapping(path = "/getByApplicationId")
    GenericResponse getByProjectId(@RequestParam("id") Long applicationId) {

        System.out.println("projectId:" + String.valueOf(applicationId));

        List<ApplicationDeploymentDto> applications = service.findByApplicationId(applicationId);
        GenericResponse result = GenericResponse.build().addKey$Value("list", applications);
        return result;
    }

    @GetMapping(path = "/getDeploymentList")
    GenericResponse getListByEnv(ApplicationReleaseListRequest request) {
        System.out.println(request.toString());
        List<ApplicationDeploymentDto> applications = service.findByEnvType(request);
        GenericResponse result = GenericResponse.build().addKey$Value("list", applications);
        return result;
    }

    @PostMapping(path = "/deployTo")
    GenericResponse deployment(@RequestBody GenericRequest req) {
        System.out.println(req.toString());
        ReleaseRequest releaseDto = req.getObject(ReleaseRequest.class);

        service.deployApplication(releaseDto);


        GenericResponse result = new GenericResponse(releaseDto);
        return result;
    }

    @PostMapping(path = "/deployWithImage")
    GenericResponse deployWithImage(@RequestBody GenericRequest req) {
        System.out.println(req.toString());
        ReleaseRequest releaseDto = req.getObject(ReleaseRequest.class);
        service.deployApplicationWithImage(releaseDto);
        GenericResponse result = new GenericResponse(releaseDto);
        return result;
    }

    @GetMapping(path = "/autoDeployment")
    GenericResponse buildDockerImage(DockerBuildRequest request) {
        System.out.println(request.toString());
        service.buildImage(request);
        GenericResponse result = new GenericResponse(request);
        return result;
    }

    @PostMapping(path = "/update/{id}")
    public GenericResponse updateSave(@RequestBody GenericRequest req, @PathVariable Long id) {
        ApplicationDeploymentDto item = req.getObject(ApplicationDeploymentDto.class);
        item.setId(id);
        System.out.println("applicationId:" + String.valueOf(id));
        service.update(item);
        GenericResponse result = new GenericResponse(item);
        return result;


    }


    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public Long removeById(@PathVariable Long id) {
        service.remove(id);
        return id;
    }


}
