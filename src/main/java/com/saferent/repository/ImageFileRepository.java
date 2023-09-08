package com.saferent.repository;

import com.saferent.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile,String> {

    @EntityGraph(attributePaths = "id")
    List<ImageFile> findAll();

    @EntityGraph(attributePaths = "id")
    Optional<ImageFile> findImageById(String id);
}
