package com.saferent.repository;

import com.saferent.domain.*;
import com.saferent.domain.enums.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {



    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH Car c on r.car=c.id WHERE " +
            "c.id=:carId and (r.status not in :status) and :pickUpTime BETWEEN r.pickUpTime and r.dropOfTime " +
            "or " +
            "c.id=:carId and (r.status not in :status) and :dropOfTime BETWEEN r.pickUpTime and r.dropOfTime " +
            "or " +
            "c.id=:carId and (r.status not in :status) and (r.pickUpTime BETWEEN :pickUpTime and :dropOfTime)")
    List<Reservation> checkCarStatus(@Param("carId") Long carId,
                                     @Param("pickUpTime") LocalDateTime pickUpTime,
                                     @Param("dropOfTime") LocalDateTime dropOfTime,
                                     @Param("status") ReservationStatus[] status);

    @EntityGraph(attributePaths = {"car","car.image"})
    List<Reservation> findAll();

    @EntityGraph(attributePaths = {"car","car.image"})
    Page<Reservation> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"car", "car.image","user"})
    Optional<Reservation> findById(Long id);

    @EntityGraph(attributePaths = {"car", "car.image","user"})
    Page<Reservation> findAllByUser(User user, Pageable pageable);


    @EntityGraph(attributePaths = {"car", "car.image","user"})
    Optional<Reservation> findByIdAndUser(Long id, User user);

    boolean existsByUser(User user);


    boolean existsByCar(Car car);
    @EntityGraph(attributePaths = {"car","user"})
    List<Reservation> findAllBy();
}
