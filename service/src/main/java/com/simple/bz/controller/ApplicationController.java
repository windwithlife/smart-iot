package com.simple.bz.controller;

import com.simple.bz.dto.ApplicationDto;
import com.simple.bz.dto.BuildRecordRequest;
import com.simple.bz.model.ApplicationModel;
import com.simple.bz.service.ApplicationService;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
import com.simple.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController extends BaseController {
    @Autowired
    ApplicationService service;


    @GetMapping(path = "/findAll")
    GenericResponse createDeployment (){

        List<ApplicationDto> dtoLst = service.findAll();
        GenericResponse result = GenericResponse.build().addKey$Value("list", dtoLst);

        return result;
    }
    @GetMapping(path = "/findById")
    GenericResponse findById (@RequestParam("id") Long applicationId){

        System.out.println("applicationId:" + String.valueOf(applicationId));

        ApplicationDto  application = service.findById(applicationId);
        GenericResponse result = GenericResponse.build().setDataObject(application);
        return result;
    }
    @PostMapping(path = "/save")
    GenericResponse save (@RequestBody GenericRequest req){

        ApplicationDto dto = req.getObject(ApplicationDto.class);
        System.out.println(dto.toString());

        service.save(dto);
        GenericResponse result = new GenericResponse(dto);
        return result;
    }

    @GetMapping(path = "/getByProjectId")
        GenericResponse getByProjectId (@RequestParam("id") Long projectId){

        System.out.println("projectId:" + String.valueOf(projectId));

        List<ApplicationDto>  applications = service.findByProjectId(projectId);
        GenericResponse result = GenericResponse.build().addKey$Value("list", applications);
        return result;
    }



    @PostMapping(path = "/update/{id}")
    public GenericResponse updateSave(@RequestBody GenericRequest req , @PathVariable Long id) {
        ApplicationDto item = req.getObject(ApplicationDto.class);
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
