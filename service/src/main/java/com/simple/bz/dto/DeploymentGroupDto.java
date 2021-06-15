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

public class DeploymentGroupDto {
    private Long     id;
    private String   name;
    private boolean  active;

    private Date     updatedDate;

    private String   supportActions;
    private String   supportMesh;
    private String   topicName;
    private String   extraTopics;
    private String   serverAddress;
    private String   serverPort;
}
