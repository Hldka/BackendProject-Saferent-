package com.saferent.repository;

import com.saferent.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository // optional
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "roles") // Defaultta Lazy olan Role bilgilerini EAGER yaptık
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = "roles") // 2 query
    List<User> findAll();  // 4 query

    @EntityGraph(attributePaths = "roles") // 2 query
    Page<User> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findById(Long id);

    @EntityGraph(attributePaths = "id") // bana Rolleri getirme
    Optional<User> findUserById(Long id);


    @Modifying // JpaRepository içinde custom bir query ile DML operasyonları yapılıyor ise @Modifying yazılır
    @Query("UPDATE User u SET u.firstName=:firstName, u.lastName=:lastName,u.phoneNumber=:phoneNumber,u.email=:email,u.address=:address,u.zipCode=:zipCode WHERE u.id=:id")
    void update(@Param("id") Long id,
                @Param("firstName") String firstName,
                @Param("lastName") String lastName,
                @Param("phoneNumber") String phoneNumber,
                @Param("email") String email,
                @Param("address") String address,
                @Param("zipCode") String zipCode);



}
