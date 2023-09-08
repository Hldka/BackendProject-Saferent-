package com.saferent.service;

import com.saferent.domain.*;
import com.saferent.exception.message.*;
import com.saferent.report.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

@Service
public class ReportService {
    private final UserService userService;
    private final CarService carService;
    private final ReservationService reservationService;

    public ReportService(UserService userService, CarService carService, ReservationService reservationService) {
        this.userService = userService;
        this.carService = carService;
        this.reservationService = reservationService;
    }

    public ByteArrayInputStream getUserReport()  {

       List<User> users =   userService.getUsers();

        try {
            return ExcelReporter.getUserExcelReport(users);
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.EXCEL_REPORT_ERROR_MESSAGE);
        }
    }

    public ByteArrayInputStream getCarReport() {

        List<Car> cars = carService.getAllCar();

        try {
            return ExcelReporter.getCarExcelReport(cars);
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.EXCEL_REPORT_ERROR_MESSAGE);
        }

    }

    public ByteArrayInputStream getReservationReport() {
        List<Reservation> reservations = reservationService.getAll();

        try {
            return ExcelReporter.getReservationExcelReport(reservations);
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.EXCEL_REPORT_ERROR_MESSAGE);
        }
    }
}
