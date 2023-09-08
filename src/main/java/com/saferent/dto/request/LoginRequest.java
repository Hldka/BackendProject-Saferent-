package com.saferent.dto.request;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Email(message = "Please provide a valid email")
    private String email;
    @NotBlank(message = "Please provide a password")
    private String password;


}
