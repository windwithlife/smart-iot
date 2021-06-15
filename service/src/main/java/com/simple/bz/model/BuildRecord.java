package com.simple.bz.model;

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
@Entity
@Table(name="BuildRecord")
public class BuildRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //名称
    private String name;
    private String buildNumber;
    private String releaseStatus;
    private String releaseVersion;
    private String releaseType;
    private String logText;
    private Long   applicationReleaseId;
    private Date   updatedDate;



}
