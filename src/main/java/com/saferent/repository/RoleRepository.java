package com.saferent.repository;

import com.saferent.domain.*;
import com.saferent.domain.enums.*;
import org.springframework.boot.autoconfigure.jackson.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByType(RoleType type);
}
