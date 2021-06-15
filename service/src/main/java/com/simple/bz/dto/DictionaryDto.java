package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DictionaryDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    //显示名称
    private String name;
    //说明
    private String description;
    //对应定义值
    private String value;

}
