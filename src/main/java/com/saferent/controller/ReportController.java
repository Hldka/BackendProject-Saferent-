package com.saferent.controller;

import com.saferent.service.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("/excel")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/download/users") // http://localhost:8080/excel/download/users
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> getUserReport() {
        String fileName = "users.xlsx";
        ByteArrayInputStream bais = reportService.getUserReport();
        InputStreamResource file = new InputStreamResource(bais);

        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName).
                contentType(MediaType.parseMediaType("application/vmd.ms-excel")).
                body(file);
    }

    // ************* CAR_REPORT ************************
    @GetMapping("/download/cars")// http://localhost:8080/excel/download/cars
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> getCarReport(){
        String fileName = "cars.xlsx";
        ByteArrayInputStream bais = reportService.getCarReport();
        InputStreamResource file = new InputStreamResource(bais);

        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName).
                contentType(MediaType.parseMediaType("application/vmd.ms-excel")).
                body(file);

    }

    //****************** RESERVATION_REPORT*********************
    @GetMapping("/download/reservations")// http://localhost:8080/excel/download/reservations
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> getReservationReport() {
        String fileName = "reservations.xlsx";
        ByteArrayInputStream bais = reportService.getReservationReport();
        InputStreamResource file = new InputStreamResource(bais);

        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName).
                contentType(MediaType.parseMediaType("application/vmd.ms-excel")).
                body(file);
    }


}
