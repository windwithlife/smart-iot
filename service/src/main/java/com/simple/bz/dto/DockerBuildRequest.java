package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DockerBuildRequest {
    private Long   applicationId;
    private String applicationName;
    private String envType;
    private String version;
}
