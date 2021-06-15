package com.simple;

import com.alibaba.fastjson.JSONObject;

public class JsonUtils {
    public static boolean isJson(String string){
        try {
            JSONObject jsonStr= JSONObject.parseObject(string);
            return  true;
        } catch (Exception e) {
            return false;
        }
    }

}
