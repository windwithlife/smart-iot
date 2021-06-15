package com.simple.bz.model;

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
@Entity
@Table(name="ApplicationPoint")
public class DeploymentGroupModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
