package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {
    private String   id;
    private String   name;
    private String   model;
    private String   supportFunction;
    private boolean  active;
    private boolean  support;
    private String   status;
    private Date     updatedDate;
    private String   locationTopic;
    private String   extraTopics;
    private String   netAddress;
    private String   netPort;
}
