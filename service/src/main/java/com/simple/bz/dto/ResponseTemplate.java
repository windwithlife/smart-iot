package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ResponseTemplate<T> {

    private String message;
    private T data;
    public void setResponse(T res){
        this.data = res;
    }

}
