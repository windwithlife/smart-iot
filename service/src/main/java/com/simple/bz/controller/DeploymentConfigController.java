package com.simple.bz.controller;

import com.simple.bz.dto.DeploymentConfigDto;
import com.simple.bz.model.ApplicationModel;
import com.simple.bz.service.ApplicationService;
import com.simple.bz.service.DeploymentConfigService;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
import com.simple.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deploymentconfig")
public class DeploymentConfigController extends BaseController {
    @Autowired
    DeploymentConfigService service;


    @GetMapping(path = "/findAll")
    GenericResponse createDeployment (){

        List<DeploymentConfigDto> dtoLst = service.findAll();
        GenericResponse result = GenericResponse.build().addKey$Value("list", dtoLst);

        return result;
    }
    @GetMapping(path = "/findById")
    GenericResponse findById (@RequestParam("id") Long applicationId){

        System.out.println("applicationId:" + String.valueOf(applicationId));

        DeploymentConfigDto  application = service.findById(applicationId);
        GenericResponse result = GenericResponse.build().setDataObject(application);
        return result;
    }
    @PostMapping(path = "/save")
    GenericResponse createDeployment (@RequestBody GenericRequest req){

        DeploymentConfigDto dto = req.getObject(DeploymentConfigDto.class);
        System.out.println(dto.toString());
        service.save(dto);
        GenericResponse result = new GenericResponse(dto);
        return result;
    }





    @PostMapping(path = "/update/{id}")
    public GenericResponse update(@RequestBody GenericRequest req, @PathVariable Long id) {
        DeploymentConfigDto item = req.getObject(DeploymentConfigDto.class);
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
