package com.simple.bz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AccountDto {
    private String id;
    private String name;
    private String password;
    private String passwordHash;
    private String token;
    private String email;
    private boolean confirmedAndActive;
    private String phoneNumber;
    private String photoUrl;
    public String createUserId(){
        return "";
    }
}
