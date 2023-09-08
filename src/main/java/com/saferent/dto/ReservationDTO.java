package com.saferent.dto;

import com.saferent.domain.*;
import com.saferent.domain.enums.*;
import lombok.*;

import javax.persistence.*;
import java.time.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;

    private CarDTO car;

    private Long userId;

    private LocalDateTime pickUpTime;

    private LocalDateTime dropOfTime;

    private String pickUpLocation;

    private String dropOfLocation;

    private ReservationStatus status;

    private Double totalPrice;
}
