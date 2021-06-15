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

public class ApplicationDeploymentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    //名称
    private String name;
    //说明
    private Long    applicationId;
    private Long    applicationPointId;
    private Long    imageId;
    private String  buildNumber;
    private String  imageLabel;
    private boolean autoDeploy;
    private String  envType;
    private String  releaseVersion;
    private String  releaseStatus;
    private int     cpu;
    private int     diskSize;
    private int     memSize;
    private int     instanceCount;



}

