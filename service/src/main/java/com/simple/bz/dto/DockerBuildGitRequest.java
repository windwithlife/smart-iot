package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DockerBuildGitRequest {
    private String applicationName;
    private String version;
    private GitHeadCommit head_commit;

}
