package com.simple.bz.controller;

import com.simple.bz.dto.RoomDto;
import com.simple.bz.service.HouseService;
import com.simple.bz.service.RoomService;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
import com.simple.common.controller.BaseController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController extends BaseController {

    private final RoomService service;


    @GetMapping(path = "/findAll")
    GenericResponse findAll (){

        List<RoomDto> dtoLst = service.findAll();
        GenericResponse result = GenericResponse.build().addKey$Value("list", dtoLst);

        return result;
    }
    @GetMapping(path = "/findById")
    GenericResponse findById (@RequestParam("id") Long applicationId){

        System.out.println("applicationId:" + applicationId);

        RoomDto  application = service.findById(applicationId);
        GenericResponse result = GenericResponse.build().setDataObject(application);
        return result;
    }
    @PostMapping(path = "/save")
    GenericResponse save (@RequestBody GenericRequest req){

        RoomDto dto = req.getObject(RoomDto.class);
        System.out.println(dto.toString());
        service.save(dto);
        GenericResponse result = new GenericResponse(dto);
        return result;
    }



    @PostMapping(path = "/update/{id}")
    public GenericResponse updateSave(@RequestBody GenericRequest req, @PathVariable Long id) {
        RoomDto dto = req.getObject(RoomDto.class);
        dto.setId(id);
        System.out.println("projectInfo:" + String.valueOf(id));
        System.out.println(dto.toString());
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
