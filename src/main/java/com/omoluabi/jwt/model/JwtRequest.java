package com.omoluabi.jwt.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest {

    private String username;

    private String password;
}
