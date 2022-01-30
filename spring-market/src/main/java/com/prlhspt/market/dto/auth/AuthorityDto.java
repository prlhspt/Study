package com.prlhspt.market.dto.auth;

import lombok.Data;

@Data
public class AuthorityDto {
    private String authorityName;

    public AuthorityDto(String authorityName) {
        this.authorityName = authorityName;
    }
}