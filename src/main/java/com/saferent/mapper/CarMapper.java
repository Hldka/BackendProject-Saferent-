package com.saferent.mapper;

import com.saferent.domain.*;
import com.saferent.dto.*;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.*;

@Mapper(componentModel = "spring")
public interface CarMapper {

    //!!! CarDTO --> Car
    @Mapping(target = "image",ignore = true)
    Car carDTOToCar(CarDTO carDTO);

    //!!! List.CarDTO --> List.Car
    List<CarDTO> map(List<Car> cars);

    //!!! Car -> CarDTO
    @Mapping(source = "image", target ="image", qualifiedByName = "getImageAsString")
    CarDTO carToCarDTO(Car car);

    @Named("getImageAsString")
    public static Set<String> getImageIds(Set<ImageFile> imageFiles) {
        Set<String> imgs = new HashSet<>();
        imgs = imageFiles.stream().
                map(imFile -> imFile.getId().toString()).
                collect(Collectors.toSet());
        return imgs;
    }
}
