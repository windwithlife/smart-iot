package com.simple.bz.controller;

import com.simple.bz.dto.CategoryDto;
import com.simple.bz.service.CategoryService;
import com.simple.bz.service.DictionaryService;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
import com.simple.common.controller.BaseController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {

    private final CategoryService service;


    @GetMapping(path = "/findAll")
    GenericResponse findAll (){

        List<CategoryDto> dtoLst = service.findAll();
        GenericResponse result = GenericResponse.build().addKey$Value("list", dtoLst);

        return result;
    }
    @GetMapping(path = "/findById")
    GenericResponse findById (@RequestParam("id") Long applicationId){

        System.out.println("applicationId:" + applicationId);

        CategoryDto  application = service.findById(applicationId);
        GenericResponse result = GenericResponse.build().setDataObject(application);
        return result;
    }
    @PostMapping(path = "/save")
    GenericResponse save (@RequestBody GenericRequest req){

        CategoryDto dto = req.getObject(CategoryDto.class);
        System.out.println(dto.toString());
        service.save(dto);
        GenericResponse result = new GenericResponse(dto);
        return result;
    }



    @PostMapping(path = "/update/{id}")
    public GenericResponse updateSave(@RequestBody GenericRequest req , @PathVariable Long id) {
        CategoryDto item = req.getObject(CategoryDto.class);
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
