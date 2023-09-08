package com.saferent.security.service;

import com.saferent.domain.User;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;
import java.util.stream.*;

@Getter
@Setter@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private String email;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    // user ---> UserDetails
    public static UserDetailsImpl build(User user) {
        List<SimpleGrantedAuthority> authorities = user.getRoles().
                stream().
                map(role -> new SimpleGrantedAuthority(role.getType().name())).
                collect(Collectors.toList());

        return new UserDetailsImpl(user.getEmail(),user.getPassword(),authorities);

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
