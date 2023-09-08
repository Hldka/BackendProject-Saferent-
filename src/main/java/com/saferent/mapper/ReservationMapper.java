package com.saferent.mapper;

import com.saferent.domain.*;
import com.saferent.dto.*;
import com.saferent.dto.request.*;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.*;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    Reservation reservationRequestToReservation(ReservationRequest reservationRequest);

    @Mapping(source="car.image", target="car.image", qualifiedByName = "getImageAsString")
    @Mapping(source="user", target="userId", qualifiedByName = "getUserId")
    ReservationDTO reservationToReservationDTO(Reservation reservation);

    List<ReservationDTO> map(List<Reservation> reservationList);

    @Named("getImageAsString")
    public static Set<String> getImageIds(Set<ImageFile> imageFiles) {
        Set<String> imgs = new HashSet<>();

        imgs = imageFiles.stream().map(imFile->imFile.getId().toString()).
                collect(Collectors.toSet());
        return imgs;
    }

    @Named("getUserId")
    public static Long getUserId(User user) {
        return user.getId();
    }
}
