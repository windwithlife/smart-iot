package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildRecordRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    //名称
    private String releaseStatus;
    private String releaseVersion;
    private String releaseType;
    private Long   applicationReleaseId;
    private int    buildType;




}
