package com.simple.bz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="tbl_house")
public class HouseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long     id;
    private String   name;
    private String   address;
    private String   userId;
    private Date     createdDate;

}
