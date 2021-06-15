package com.simple.bz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="EndPoint")
public class EndPointModel {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(columnDefinition = "varchar(250)")
    private String   id;
    private String   name;
    private String   model;
    private String   supportActions;
    private String   supportType;
    private String   supportDomain;
    private boolean  active;

    private String   status;
    private Date     updatedDate;
    private String   locationTopic;
    private String   extraTopics;
    private String   serverAddress;
    private String   serverPort;
}
