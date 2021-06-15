package com.simple.bz.controller;

import com.simple.bz.dto.DeploymentGroupDto;
import com.simple.bz.service.DeploymentConfigService;
import com.simple.bz.service.DeploymentGroupService;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
import com.simple.common.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/deploymentgroup")
public class DeploymentGroupController extends BaseController {

    private final DeploymentGroupService service;

    @GetMapping(path = "/findAll")
    GenericResponse createDeployment (){

        List<DeploymentGroupDto> dtoLst = service.findAll();
        GenericResponse result = GenericResponse.build().addKey$Value("list", dtoLst);

        return result;
    }
    @GetMapping(path = "/findById")
    GenericResponse findById (@RequestParam("id") Long id){

        System.out.println("applicationId:" + String.valueOf(id));

        DeploymentGroupDto  application = service.findById(id);
        GenericResponse result = GenericResponse.build().setDataObject(application);
        return result;
    }
    @PostMapping(path = "/save")
    GenericResponse createDeployment (@RequestBody GenericRequest req){

        DeploymentGroupDto dto = req.getObject(DeploymentGroupDto.class);
        System.out.println(dto.toString());
        service.save(dto);
        GenericResponse result = new GenericResponse(dto);
        return result;
    }





    @PostMapping(path = "/update/{id}")
    public GenericResponse update(@RequestBody GenericRequest req, @PathVariable Long id) {
        DeploymentGroupDto item = req.getObject( DeploymentGroupDto.class);
        item.setId(id);
        System.out.println("applicationId:" + String.valueOf(id));
        service.update(item);
        GenericResponse result = new GenericResponse(item);
        return result;


    }

    @PostMapping(path = "/update")
    public GenericResponse updateSave(@RequestBody DeploymentGroupDto item) {
        System.out.println("deployment config item:" + item.toString());
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
