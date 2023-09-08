package com.saferent.dto.request;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank(message="Please Provide Old Password")
    private String oldPassword;

    @NotBlank(message="Please Provide New Password")
    private String newPassword;
}
