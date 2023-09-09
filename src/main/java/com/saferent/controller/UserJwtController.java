package com.saferent.controller;

import com.saferent.dto.request.*;
import com.saferent.dto.response.*;
import com.saferent.security.jwt.*;
import com.saferent.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
public class UserJwtController {
    // !!! Only Login and Register transactions will be made in this class
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // !!! Register
    @PostMapping("/register")
    public ResponseEntity<SfResponse> registerUser(@Valid
                 @RequestBody RegisterRequest registerRequest) {
        userService.saveUser(registerRequest);

        SfResponse response = new SfResponse();
        response.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    // !!! Login

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid
                           @RequestBody LoginRequest loginRequest) {

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword());

             Authentication authentication =
                  authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            // !!! The user was validated at this stage and Token production is underway
             UserDetails userDetails = (UserDetails) authentication.getPrincipal();
             String jwtToken = jwtUtils.generateJwtToken(userDetails);
             // !!! JWT token is sent to client
            LoginResponse loginResponse = new LoginResponse(jwtToken);

            return new ResponseEntity<>(loginResponse,HttpStatus.OK);

    }
}
