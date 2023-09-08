package com.saferent.dto;

import com.saferent.domain.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {


    private Long id;
    @Size(max=30 , message="Size is exceeded")
    @NotBlank(message="Please provide car model")
    private String model;
    @NotNull(message = "Please provide car door info")
    private Integer doors;
    @NotNull(message = "Please provide car seat info")
    private Integer seats;
    @NotNull(message = "Please provide car luggage info")
    private Integer luggage;

    @NotBlank(message="Please provide car transmission info")
    private String transmission;
    @NotNull(message = "Please provide car airCondition info")
    private Boolean airConditioning;
    @NotNull(message = "Please provide car age info")
    private Integer age;
    @NotNull(message = "Please provide car per hour price")
    private Double pricePerHour;
    @Size(max=30 , message="Size is exceeded")
    @NotBlank(message="Please provide car fuel type")
    private  String fuelType;
    private Boolean builtIn = false;

    private Set<String> image;
}







