package com.saferent.dto.request;

import com.saferent.domain.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Size(max=50)
    @NotBlank(message="Please provide your First Name")
    // @NotBlank = charSequence.toString().trim().length()>0
    private String firstName;

    @Size(max=50)
    @NotBlank(message="Please provide your Last Name")
    private String lastName;

    @Size(min=5 ,max=80)
    @Email(message = "Please provide valid e-mail")
    private String email;

    @Size(min=4 , max=20, message="Please provide Correct Size of Password")
    @NotBlank(message="Please provide your password")
    private String password;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", //(541) 317-8828
            message = "Please provide valid phone number")
    @Size(min=14, max=14)
    @NotBlank(message = "Please provide your phone number")
    private String phoneNumber;

    @Size(max=100)
    @NotBlank(message="Please provide your address")
    private String address;

    @Size(max=15)
    @NotBlank(message="Please provide your zip code")
    private String zipCode;

}
