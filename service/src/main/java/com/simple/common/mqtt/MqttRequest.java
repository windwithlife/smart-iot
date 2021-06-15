package com.simple.common.mqtt;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MqttRequest {
    private String     version;
    private String     command;
    private String     token;
    private JSONObject params;
    public  <T>  T getParamObject(Class<T> clazz){
        if (null == this.params){return null;}
        return this.params.toJavaObject(clazz);
    }
}
