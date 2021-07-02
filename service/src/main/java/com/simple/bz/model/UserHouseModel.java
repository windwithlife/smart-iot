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
@Table(name="tbl_user_house")
public class UserHouseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long     id;
    private String   userId;
    private Long     houseId;

}
