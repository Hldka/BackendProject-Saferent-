package com.saferent.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="t_car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30,nullable = false)
    private String model;
    @Column(nullable = false)
    private Integer doors;
    @Column(nullable = false)
    private Integer seats;
    @Column(nullable = false)
    private Integer luggage;

    @Column(length = 30, nullable = false)
    private String transmission;
    @Column(nullable = false)
    private Boolean airConditioning;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private Double pricePerHour;
    @Column(length = 30,nullable = false)
    private  String fuelType;
    private Boolean builtIn = false;
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name="car_id")
    private Set<ImageFile> image;


}
