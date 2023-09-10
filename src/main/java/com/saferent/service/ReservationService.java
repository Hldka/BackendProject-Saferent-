package com.saferent.service;

import com.saferent.domain.*;
import com.saferent.domain.enums.*;
import com.saferent.dto.*;
import com.saferent.dto.request.*;
import com.saferent.exception.*;
import com.saferent.exception.message.*;
import com.saferent.mapper.*;
import com.saferent.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.time.temporal.*;
import java.util.*;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    public void createReservation(ReservationRequest reservationRequest, User user, Car car) {

        checkReservationTimeIsCorrect(reservationRequest.getPickUpTime(), reservationRequest.getDropOfTime());

        boolean carStatus = checkCarAvailability(car , reservationRequest.getPickUpTime(), reservationRequest.getDropOfTime());

        Reservation reservation = reservationMapper.reservationRequestToReservation(reservationRequest);

        if(carStatus){
            reservation.setStatus(ReservationStatus.CREATED);
        } else {
            throw new BadRequestException(ErrorMessage.CAR_NOT_AVAILABLE_MESSAGE);
        }
        reservation.setCar(car);
        reservation.setUser(user);

        Double totalPrice = getTotalPrice(car, reservationRequest.getPickUpTime(),reservationRequest.getDropOfTime());

        reservation.setTotalPrice(totalPrice);

        reservationRepository.save(reservation);

    }
    // !!! Are the reservation dates correct  ???
    public void checkReservationTimeIsCorrect(LocalDateTime pickUpTime,
                                               LocalDateTime dropOfTime){
        LocalDateTime now = LocalDateTime.now();

        if(pickUpTime.isBefore(now)) {
            throw new BadRequestException(ErrorMessage.RESERVATION_TIME_INCORRECT_MESSAGE);
        }

        //!!! Is the start date and end date equal to one
         boolean isEqual = pickUpTime.isEqual(dropOfTime)?true:false;
        // !!! Is the start date before the end date?
         boolean isBefore = pickUpTime.isBefore(dropOfTime)?true:false; // !!!

         if(isEqual || !isBefore){
             throw  new BadRequestException(ErrorMessage.RESERVATION_TIME_INCORRECT_MESSAGE);
         }

    }

    // !!! Is the vehicle available ???
    public boolean checkCarAvailability(Car car,LocalDateTime pickUpTime,
                                         LocalDateTime dropOfTime) {

        List<Reservation> existReservations = getConflictReservations(car,pickUpTime,dropOfTime);

        return existReservations.isEmpty();

    }

    // !!! price calculation
    public Double getTotalPrice(Car car,LocalDateTime pickUpTime,
                                 LocalDateTime dropOfTime){
        Long minutes =  ChronoUnit.MINUTES.between(pickUpTime,dropOfTime);
        double hours = Math.ceil(minutes/60.0);
         return car.getPricePerHour() * hours;

    }

    // !!!Is there a conflict between reservations ???
    public List<Reservation> getConflictReservations(Car car,LocalDateTime pickUpTime,
                                                      LocalDateTime dropOfTime ){
        if(pickUpTime.isAfter(dropOfTime)){
            throw  new BadRequestException(ErrorMessage.RESERVATION_TIME_INCORRECT_MESSAGE);
        }

        ReservationStatus[] status = {ReservationStatus.CANCELED,ReservationStatus.DONE};

        List<Reservation> existReservation =
                reservationRepository.checkCarStatus(car.getId(),pickUpTime,dropOfTime,status);

        return existReservation;
    }

    public List<ReservationDTO> getAllReservations() {
         List<Reservation> reservations = reservationRepository.findAll();
         return reservationMapper.map(reservations);
    }

    public Page<ReservationDTO> getAllWithPage(Pageable pageable) {

        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);
        return reservationPage.map(reservationMapper::reservationToReservationDTO);
    }

    public void updateReservation(Long reservationId, Car car, ReservationUpdateRequest reservationUpdateRequest) {
        Reservation reservation = getById(reservationId);
        // !!! If the reservation status is "cancel" or "done", do not apply
        if(reservation.getStatus().equals(ReservationStatus.CANCELED) ||
                reservation.getStatus().equals(ReservationStatus.DONE))   {
            throw new BadRequestException(ErrorMessage.RESERVATION_STATUS_CANT_CHANGE_MESSAGE);
        }
        // !!! Do not check pickUpTime and DropOfTime if the status is not to be created while the reference is to be update
        if(reservationUpdateRequest.getStatus() != null &&
            reservationUpdateRequest.getStatus()== ReservationStatus.CREATED) {
            checkReservationTimeIsCorrect(reservationUpdateRequest.getPickUpTime(),
                    reservationUpdateRequest.getDropOfTime());
            // !!! ConflictControl
            List<Reservation> conflictReservations = getConflictReservations(car,
                    reservationUpdateRequest.getPickUpTime(),
                    reservationUpdateRequest.getDropOfTime());
            if(!conflictReservations.isEmpty()) {
                if(!(conflictReservations.size()==1 &&
                        conflictReservations.get(0).getId().equals(reservationId))) {
                    throw  new BadRequestException(ErrorMessage.CAR_NOT_AVAILABLE_MESSAGE);
                }
            }
            // !!! price-calculation
            Double totalPrice = getTotalPrice(car,reservationUpdateRequest.getPickUpTime(),reservationUpdateRequest.getDropOfTime());

            reservation.setTotalPrice(totalPrice);
            reservation.setCar(car);

        }
        reservation.setPickUpTime(reservationUpdateRequest.getPickUpTime());
        reservation.setDropOfTime(reservationUpdateRequest.getDropOfTime());
        reservation.setDropOfLocation(reservationUpdateRequest.getDropOfLocation());
        reservation.setPickUpLocation(reservationUpdateRequest.getPickUpLocation());
        reservation.setStatus(reservationUpdateRequest.getStatus());

        reservationRepository.save(reservation);
    }

    public Reservation getById(Long id) {
        Reservation reservation =  reservationRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION,id)));

        return reservation;
    }

    public ReservationDTO getReservationDTO(Long id) {
        Reservation reservation = getById(id);
        return reservationMapper.reservationToReservationDTO(reservation);
    }

    public Page<ReservationDTO> findReservationPageByUser(User user, Pageable pageable) {

        Page<Reservation> reservationPage =  reservationRepository.findAllByUser(user,pageable);

        return reservationPage.map(reservationMapper::reservationToReservationDTO);
    }

    public ReservationDTO findByIdAndUser(Long id, User user) {
        Reservation reservation = reservationRepository.findByIdAndUser(id,user).
                orElseThrow(()-> new ResourceNotFoundException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));
        return reservationMapper.reservationToReservationDTO(reservation);
    }

    public void removeById(Long id) {
        // !!! Is there Any??
        boolean exist = reservationRepository.existsById(id);

        if(!exist) {
            throw  new ResourceNotFoundException(
                    String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION,id));
        }

        reservationRepository.deleteById(id);
    }

    public boolean existByCar(Car car) {
        return reservationRepository.existsByCar(car);
    }


    public boolean existByUser(User user) {
        return reservationRepository.existsByUser(user);
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAllBy();
    }
}












