package com.saferent.dto;

import com.saferent.domain.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    // !!! bu class repodan gelen pojo yu DTO ya çevirmek için kullanılacak

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private Boolean builtIn ;

    private Set<String> roles;

    public void setRoles(Set<Role> roles) {
        Set<String> roleStr = new HashSet<>();
        roles.forEach(r->{
            roleStr.add(r.getType().getName()); // Customer , Administrator
        });

        this.roles=roleStr;

    }
}
