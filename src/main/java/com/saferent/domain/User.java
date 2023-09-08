package com.saferent.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 80, nullable = false, unique = true)
    private String email;

    @Column(length = 120, nullable = false)
    private String password;

    @Column(length = 14, nullable = false)
    private String phoneNumber;

    @Column(length = 15, nullable = false)
    private String address;

    @Column(length = 15, nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private Boolean builtIn = false ;

    @ManyToMany // LAZY
    @JoinTable( name="t_user_role",
                joinColumns = @JoinColumn(name="user_id"),
                inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();









}
