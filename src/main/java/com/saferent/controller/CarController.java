package com.saferent.controller;

import com.saferent.dto.*;
import com.saferent.dto.response.*;
import com.saferent.service.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // !!! SaveCar
    @PostMapping("/admin/{imageId}/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SfResponse> saveCar(
            @PathVariable String imageId, @Valid @RequestBody CarDTO carDTO){
        carService.saveCar(imageId,carDTO);

        SfResponse response = new SfResponse(
                ResponseMessage.CAR_SAVED_RESPONSE_MESSAGE,true);
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }
    // !!! getAllCar
    @GetMapping("/visitors/all")
    public ResponseEntity<List<CarDTO>> getAllCars(){
        List<CarDTO> allCars = carService.getAllCars();
        return ResponseEntity.ok(allCars);
    }

    // !!! getAllWithPage
    @GetMapping("/visitors/pages")
    public ResponseEntity<Page<CarDTO>> getAllCarsWithPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam(value="direction",
            required = false,
            defaultValue = "DESC")Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));

        Page<CarDTO> pageDTO =  carService.findAllWithPage(pageable);
        return ResponseEntity.ok(pageDTO);
    }

    // !!! getCarById
    @GetMapping("/visitors/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {

        CarDTO carDTO = carService.findById(id);
        return ResponseEntity.ok(carDTO);
    }

    //!!! Update Car with ImageId
    @PutMapping("/admin/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SfResponse> updateCar(
             @RequestParam("id") Long id,
             @RequestParam("imageId") String imageId,
             @Valid @RequestBody CarDTO carDTO) {
        carService.updateCar(id,imageId,carDTO);
        SfResponse response = new SfResponse(
                ResponseMessage.CAR_UPDATE_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(response);
    }

    // !!! Delete

    @DeleteMapping("/admin/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SfResponse> deleteCar(@PathVariable Long id) {
        carService.removeById(id);

        SfResponse response =
                new SfResponse(ResponseMessage.CAR_DELETE_RESPONSE_MESSAGE,true);
        return  ResponseEntity.ok(response);
    }


}
