package com.saferent.mapper;

import com.saferent.domain.Car;
import com.saferent.domain.Reservation;
import com.saferent.dto.CarDTO;
import com.saferent.dto.ReservationDTO;
import com.saferent.dto.request.ReservationRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-08T09:58:39+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {

    @Override
    public Reservation reservationRequestToReservation(ReservationRequest reservationRequest) {
        if ( reservationRequest == null ) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setPickUpTime( reservationRequest.getPickUpTime() );
        reservation.setDropOfTime( reservationRequest.getDropOfTime() );
        reservation.setPickUpLocation( reservationRequest.getPickUpLocation() );
        reservation.setDropOfLocation( reservationRequest.getDropOfLocation() );

        return reservation;
    }

    @Override
    public ReservationDTO reservationToReservationDTO(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setCar( carToCarDTO( reservation.getCar() ) );
        reservationDTO.setUserId( ReservationMapper.getUserId( reservation.getUser() ) );
        reservationDTO.setId( reservation.getId() );
        reservationDTO.setPickUpTime( reservation.getPickUpTime() );
        reservationDTO.setDropOfTime( reservation.getDropOfTime() );
        reservationDTO.setPickUpLocation( reservation.getPickUpLocation() );
        reservationDTO.setDropOfLocation( reservation.getDropOfLocation() );
        reservationDTO.setStatus( reservation.getStatus() );
        reservationDTO.setTotalPrice( reservation.getTotalPrice() );

        return reservationDTO;
    }

    @Override
    public List<ReservationDTO> map(List<Reservation> reservationList) {
        if ( reservationList == null ) {
            return null;
        }

        List<ReservationDTO> list = new ArrayList<ReservationDTO>( reservationList.size() );
        for ( Reservation reservation : reservationList ) {
            list.add( reservationToReservationDTO( reservation ) );
        }

        return list;
    }

    protected CarDTO carToCarDTO(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDTO carDTO = new CarDTO();

        carDTO.setImage( ReservationMapper.getImageIds( car.getImage() ) );
        carDTO.setId( car.getId() );
        carDTO.setModel( car.getModel() );
        carDTO.setDoors( car.getDoors() );
        carDTO.setSeats( car.getSeats() );
        carDTO.setLuggage( car.getLuggage() );
        carDTO.setTransmission( car.getTransmission() );
        carDTO.setAirConditioning( car.getAirConditioning() );
        carDTO.setAge( car.getAge() );
        carDTO.setPricePerHour( car.getPricePerHour() );
        carDTO.setFuelType( car.getFuelType() );
        carDTO.setBuiltIn( car.getBuiltIn() );

        return carDTO;
    }
}
