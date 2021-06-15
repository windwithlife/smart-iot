package com.simple.bz.dto;

import com.simple.bz.model.ApplicationModel;
import com.simple.bz.model.ApplicationTypeModel;
import com.simple.bz.model.DeploymentConfigModel;
import com.simple.bz.model.ProjectModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReleaseDetail {
    private Long   id;
    private String name;
    private String releaseVersion;
    private String applicationName;
    private String domainName;
    private Long   buildId;
    private String buildNumber;
    private String imageLabel;
    private String supportMesh;
    private String envType;
    private boolean autoDeploy;
    private int     cpu;
    private int     diskSize;
    private int     memSize;
    private int     instanceCount;
    private ProjectModel projectInfo;
    private ApplicationModel applicationInfo;
    private DeploymentConfigModel deploymentConfig;
    private ApplicationTypeModel applicationTypeInfo;
}
