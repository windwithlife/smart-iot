package com.simple.bz.dto;

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

public class ApplicationTypeDto implements Serializable {
    private static final long serialVersionUID = 1L;

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
