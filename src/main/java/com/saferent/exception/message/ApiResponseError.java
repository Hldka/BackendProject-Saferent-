package com.saferent.exception.message;

import com.fasterxml.jackson.annotation.*;
import org.springframework.http.*;

import java.time.*;

public class ApiResponseError {
    // AIM: Fighting the main soblon of custom error messages



    private HttpStatus status;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private String requestURI ;

    // Const

    private ApiResponseError(){
        timestamp = LocalDateTime.now();
    }

    public ApiResponseError(HttpStatus status){
        this(); // paramethress-free private const. Calling
        this.message="Unexpected Error";
        this.status = status ;

    }

    public ApiResponseError(HttpStatus status, String message, String requestURI) {
        this(status); //1 parameter above, public const. calling
        this.message = message;
        this.requestURI = requestURI;
    }



    // GETTER -SETTER


    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }
}
