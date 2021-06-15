package com.simple.bz.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="ApplicationType")
public class ApplicationTypeModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //名称
    private String name;
    private String idName;
    //说明
    private String description;
    private String sideType;

    private boolean needBuildAndInstall;
    private boolean needBuildDocker;
    private boolean needExecuteScript;
    private boolean needDeploy;
    private boolean deployConfigPath;
    private String  buildCommand;
    private String  deployCommand;
    private String  dockerfile;
    private String  deployTemplate;
}
