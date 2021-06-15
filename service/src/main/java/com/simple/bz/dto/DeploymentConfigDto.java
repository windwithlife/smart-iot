package com.simple.bz.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeploymentConfigDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long   id;
    private Long   applicationId;
    private String path;
    private String repository;
    private String repositoryBranch;
    private String targetPath;
    private String domainName;
    private String applicationName;
    private String version;


}

