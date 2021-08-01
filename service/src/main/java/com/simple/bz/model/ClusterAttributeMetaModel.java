package com.simple.bz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="tbl_meta_cluster_attribute")
public class ClusterAttributeMetaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long     id;
    private String   cluster;            //cluster编号
    private String   statusOrCommand;    //是传感遥测还是控制信令。
    private String   attribute;          //cluster属性编号
    private String   attributeName;      //设备类型识别
    private String   attributeText;      //设备可读名称
    private String   valueType;          //数据类型 枚举（比如状态）数字，
    private String   valueUnit;          //值单位，
    private String   valueRange;         //JSON数组对数据表达[{key:"0",val:"干燥"},{key:"1",val:"湿润"}],[{key:"min",val:"干燥"},{key:"max",val:"湿润"}]
    private String   controlType;        //控制类型
    private String   defaultValue;       //控制类型

}
