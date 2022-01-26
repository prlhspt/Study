package prlhspt.tutorial.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;
}
