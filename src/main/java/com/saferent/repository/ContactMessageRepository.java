package com.saferent.repository;

import com.saferent.domain.*;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository // optional
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}
