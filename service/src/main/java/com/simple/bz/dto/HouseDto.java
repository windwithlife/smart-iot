package com.simple.bz.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="房子对象",description="房子数据传输对象，用以表达请求参数或者返回参数")
public class HouseDto {
    private Long     id;
    private String   name;
    private String   address;
    private String   userId;
    private Date     createdDate;

}
