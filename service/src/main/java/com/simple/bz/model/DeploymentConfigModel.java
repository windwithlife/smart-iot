package com.simple.bz.model;


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
@Table(name="DeploymentConfig")
public class DeploymentConfigModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long   id;
    private Long   applicationId;
    private String path;
    private String repository;
    private String repositoryBranch;
    private String targetPath;
    private String applicationName;
    private String version;


}

