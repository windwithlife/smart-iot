package com.simple.bz.controller;


import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;


import com.simple.bz.dto.ExampleVO;

import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;

import com.simple.common.redis.MessageQueueProxy;
import com.simple.common.redis.RedisMessageQueueClient;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
@RequestMapping("/mqtt")
@Validated
@Api(tags = "消息相关的服务集")
public class MessageController {
    private final MessageQueueProxy mqProxy;
    static final ILogger logger = SLoggerFactory.getLogger(MessageController.class);



    @GetMapping(path = "/test")
    GenericResponse test5(@RequestParam(value = "request") String request){
        System.out.println("received  params is" +  request);

        return GenericResponse.build().addKey$Value("result",request);
    }


    @GetMapping(path = "/testmq")
    GenericResponse testMQ(@RequestParam(value = "msg") String request){
        System.out.println("received  params is" +  request);
        mqProxy.sendMessage("test","this is a test message for redis mq");
        mqProxy.sendMessage("WebSocketMessage","target-->websocket");
        return GenericResponse.build().addKey$Value("result",request);
    }


    @PostMapping(path = "/test6")
    GenericResponse test6(@RequestBody GenericRequest req){
       GenericResponse result = new GenericResponse();
       result.setDataObject(ExampleVO.builder().email(req.getString("email")).name(req.getString("name")).build());
       return result;
    }





}
