package com.simple.bz.model;

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
@Entity
@Table(name="tbl_gateway")
public class GatewayModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long     id;
    private Long     houseId;
    private String   name;
    private String   nickName;
    private String   macAddress;
    private String   model;
    private String   code;

    private boolean  active;
    private Date     upTime;
    private Date     createDate;
    private String   locationTopic;
    private int      mqttCount;

}
