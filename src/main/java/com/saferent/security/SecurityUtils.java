package com.saferent.security;

import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;

public class SecurityUtils {

    // !!! Controller veya Servis Katmanında anlık olarak login olan kullanıcıya
        // ulaşmak için bu classı yazdık
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication =securityContext.getAuthentication();

        return Optional.ofNullable(extractPrincipal(authentication));

    }

    private static String extractPrincipal(Authentication authentication){
        if(authentication==null) {
            return null;
        } else if(authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        } else if(authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }

        return null;
    }
}
