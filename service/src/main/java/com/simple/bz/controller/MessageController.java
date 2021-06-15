package com.simple.bz.controller;


import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;


import com.simple.bz.dto.ExampleVO;

import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/mqtt")
@Validated
public class MessageController {


    static final ILogger logger = SLoggerFactory.getLogger(MessageController.class);



    @GetMapping(path = "/test")
    GenericResponse test5(@RequestParam(value = "request") String request){
        System.out.println("received  params is" +  request);

        return GenericResponse.build().addKey$Value("result",request);
    }



    @PostMapping(path = "/test6")
    GenericResponse test6(@RequestBody GenericRequest req){
       GenericResponse result = new GenericResponse();
       result.setDataObject(ExampleVO.builder().email(req.getString("email")).name(req.getString("name")).build());
       return result;
    }





}
