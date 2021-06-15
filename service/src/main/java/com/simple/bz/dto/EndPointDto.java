package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EndPointDto {
    private String   id;
    private String   name;
    private String   model;
    private String   supportActions;
    private boolean  active;
    private String   supportType;
    private String   status;
    private Date     updatedDate;
    private String   locationTopic;
    private String   extraTopics;
    private String   netAddress;
    private String   netPort;
}
