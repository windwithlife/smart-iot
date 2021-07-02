package com.simple.bz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class HouseUsersDto {

    private Long     id;
    private String   userId;
    private String   token;
    private Long     houseId;

}
