package com.simple.bz.controller;

import com.simple.bz.dto.DictionaryDto;
import com.simple.bz.service.DictionaryService;
import com.simple.bz.service.ProjectService;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
import com.simple.common.controller.BaseController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController {

    private final DictionaryService service;

    @GetMapping(path = "/findAll")
    GenericResponse findAll (){

        List<DictionaryDto> dtoLst = service.findAll();
        GenericResponse result = GenericResponse.build().addKey$Value("list", dtoLst);

        return result;
    }
    @GetMapping(path = "/findById")
    GenericResponse findById (@RequestParam("id") Long applicationId){

        System.out.println("applicationId:" + applicationId);

        DictionaryDto  application = service.findById(applicationId);
        GenericResponse result = GenericResponse.build().setDataObject(application);
        return result;
    }
    @PostMapping(path = "/save")
    GenericResponse save (@RequestBody GenericRequest req){

        DictionaryDto dto = req.getObject(DictionaryDto.class);
        System.out.println(dto.toString());
        service.save(dto);
        GenericResponse result = new GenericResponse(dto);
        return result;
    }



    @PostMapping(path = "/update/{id}")
    public GenericResponse updateSave(@RequestBody GenericRequest req, @PathVariable Long id) {
        DictionaryDto item = req.getObject(DictionaryDto.class);
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

    @ResponseBody
    @RequestMapping(value = "/queryByCategoryName", method = RequestMethod.GET)
    public List<DictionaryDto> findByCategoryName(@RequestParam("categoryName") String categoryName) {
        System.out.println("input param category:" + categoryName);
        List<DictionaryDto>  dtos = service.findByCategoryName(categoryName);
        return dtos;
    }




}
