package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class ApplicationDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    //名称
    private String name;
    //说明
    private String description;
    private Long projectId;
    private Long applicationTypeId;
    private Long deploymentConfigId;
    private String applicationName;
    private String path;
    private String domainName;

}
