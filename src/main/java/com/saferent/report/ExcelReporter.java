package com.saferent.report;

import com.saferent.domain.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.*;

public class ExcelReporter {

    // !!! USER ******************
    static String SHEET_USER = "Users";
    static String[] USER_HEADERS = {"id","FirstName","LastName","PhoneNumber",
    "Email","Address","ZipCode","Roles"};

    // !!! CAR ******************
    static String SHEET_CAR="Cars";
    static  String[] CAR_HEADERS = {"id", "Model", "Doors", "Seats",
            "Luggage", "Transmission", "AirConditioning",
            "Age", "PricePerHour", "FuelType"};

    // !!! RESERVATION ******************
    static String SHEET_RESERVATION ="Reservations";
    static String[] RESERVATION_HEADERS ={"id", "CarId", "CarModel",
            "CustomerId", "CustomerFullName",
            "CustomerPhoneNumber",
            "PickUpTime", "DropOffTime",
            "PickUpLocation",
            "DropOffLocation", "Status"};

    //*********************************************************
    //*******************USER_REPORT***********************
    //*********************************************************
    public static ByteArrayInputStream getUserExcelReport(List<User> users) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
         Sheet sheet = workbook.createSheet(SHEET_USER);
         Row headerRow =  sheet.createRow(0);

         // header row dolduruluyor
        for(int i=0; i< USER_HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(USER_HEADERS[i]);
        }

        // dataları dolduruyoruz
        int rowId = 1;
        for(User user : users) {
            Row row = sheet.createRow(rowId++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getFirstName());
            row.createCell(2).setCellValue(user.getLastName());
            row.createCell(3).setCellValue(user.getPhoneNumber());
            row.createCell(4).setCellValue(user.getEmail());
            row.createCell(5).setCellValue(user.getAddress());
            row.createCell(6).setCellValue(user.getZipCode());

            // Customer , Administrator
            StringJoiner sj = new StringJoiner(",");

            for(Role role : user.getRoles()) {
                sj.add(role.getType().getName());
            }

            row.createCell(7).setCellValue(sj.toString());


        }
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());

    }

    //*********************************************************
    //*******************CAR_REPORT***********************
    //*********************************************************
    public static ByteArrayInputStream getCarExcelReport(List<Car> cars) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet(SHEET_CAR);
        Row headerRow =  sheet.createRow(0);

        // header row dolduruluyor
        for(int i=0; i< CAR_HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(CAR_HEADERS[i]);
        }

        // dataları dolduruyoruz
        int rowId = 1;
        for(Car car : cars) {
            Row row = sheet.createRow(rowId++);
            row.createCell(0).setCellValue(car.getId());
            row.createCell(1).setCellValue(car.getModel());
            row.createCell(2).setCellValue(car.getDoors());
            row.createCell(3).setCellValue(car.getSeats());
            row.createCell(4).setCellValue(car.getLuggage());
            row.createCell(5).setCellValue(car.getTransmission());
            row.createCell(6).setCellValue(car.getAirConditioning() ? "+" : "-");
            row.createCell(7).setCellValue(car.getAge());
            row.createCell(8).setCellValue(car.getPricePerHour());
            row.createCell(9).setCellValue(car.getFuelType());


        }
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());

    }

    //*********************************************************
    //*******************RESERVATION_REPORT********************
    //*********************************************************
    public static ByteArrayInputStream getReservationExcelReport(List<Reservation> reservations) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet(SHEET_RESERVATION);
        Row headerRow =  sheet.createRow(0);

        // header row dolduruluyor
        for(int i=0; i< RESERVATION_HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(RESERVATION_HEADERS[i]);
        }

        // dataları dolduruyoruz
        int rowId = 1;
        for (Reservation reservation : reservations) {
            Row row = sheet.createRow(rowId++);

            row.createCell(0).setCellValue(reservation.getId());
            row.createCell(1).setCellValue(reservation.getCar().getId());
            row.createCell(2).setCellValue(reservation.getCar().getModel());
            row.createCell(3).setCellValue(reservation.getUser().getId());
            row.createCell(4).setCellValue(reservation.getUser().getFirstName() + " " + reservation.getUser().getLastName());
            row.createCell(5).setCellValue(reservation.getUser().getPhoneNumber());
            row.createCell(6).setCellValue(reservation.getPickUpTime().toString());
            row.createCell(7).setCellValue(reservation.getDropOfTime().toString());
            row.createCell(8).setCellValue(reservation.getPickUpLocation().toString());
            row.createCell(9).setCellValue(reservation.getDropOfLocation().toString());
            row.createCell(10).setCellValue(reservation.getStatus().toString());


        }
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());

    }

}
