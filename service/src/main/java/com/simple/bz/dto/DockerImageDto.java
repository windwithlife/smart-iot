package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DockerImageDto {
    private Long     id;
    private String   name;
    private String   buildName;
    private String   buildNumber;
    private String   imageLabel;
    private String   version;
    private Long     applicationId;
    private Long     deploymentId;
    private Date     updatedDate;

}
