package com.prlhspt.market.config.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberSearchCondition {
    private String username;

    @Builder
    public MemberSearchCondition(String username) {
        this.username = username;
    }
}
