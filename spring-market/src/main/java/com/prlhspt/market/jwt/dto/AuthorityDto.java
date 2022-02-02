package com.prlhspt.market.jwt.dto;

import lombok.Data;

@Data
public class AuthorityDto {
    private String authorityName;

    public AuthorityDto(String authorityName) {
        this.authorityName = authorityName;
    }
}