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
@Table(name="ApplicationRelease")
public class ApplicationDeploymentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //名称
    private String name;
    //说明
    private Long    applicationId;
    private Long    applicationPointId;
    private Long    imageId;
    private String  buildNumber;
    private String  imageLabel;
    @Column(columnDefinition="tinyint(1) default 0")
    private boolean autoDeploy;
    private String  envType;
    private String  releaseVersion;
    private String  releaseStatus;
    @Column(columnDefinition="int(1) default 1")
    private int     cpu;
    @Column(columnDefinition="int(1) default 10")
    private int     diskSize;
    @Column(columnDefinition="int(1) default 1")
    private int     memSize;
    @Column(columnDefinition="int(1) default 1")
    private int     instanceCount;

    @Column(columnDefinition="tinyint(1) default 0")
    private boolean useOwnDeploymentFile;





}

