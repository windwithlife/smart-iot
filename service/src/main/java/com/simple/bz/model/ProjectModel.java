package com.simple.bz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="Project")
public class ProjectModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //名称
    private String name;
    //说明
    private String description;

    //站点
    private String domainName;
    private String domainNameUAT;
    //SOA地址
    private String gateway;
    private String gatewayUAT;
    //发布站点
    private Long  buildGroupId;
    private Long  uatGroupId;
    private Long  prodGroupId;

}
