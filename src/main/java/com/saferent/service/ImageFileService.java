package com.saferent.service;

import com.saferent.domain.*;
import com.saferent.dto.*;
import com.saferent.exception.*;
import com.saferent.exception.message.*;
import com.saferent.repository.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.support.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

@Service
public class ImageFileService {

    private final ImageFileRepository imageFileRepository;

    public ImageFileService(ImageFileRepository imageFileRepository) {
        this.imageFileRepository = imageFileRepository;
    }

    public String saveImage(MultipartFile file) {

        ImageFile imageFile = null;
        // !!! name
        String fileName =
                StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        //!!! Data
        try {
            ImageData imData = new ImageData(file.getBytes());
            imageFile = new ImageFile(fileName,file.getContentType(),imData);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        imageFileRepository.save(imageFile);

        return imageFile.getId();
    }

    public ImageFile getImageById(String id) {
         ImageFile imageFile = imageFileRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE,id)));
         return imageFile ;
    }

    public List<ImageFileDTO> getAllImages() {
        List<ImageFile> imageFiles = imageFileRepository.findAll();
        // image1 : localhost:8080/files/download/id

        List<ImageFileDTO> imageFileDTOS =imageFiles.stream().map(imFile->{
            // URI olusturulmasini sagliyacagiz
            String imageUri = ServletUriComponentsBuilder.
                    fromCurrentContextPath(). // localhost:8080
                    path("/files/download/"). // localhost:8080/files/download
                    path(imFile.getId()).toUriString();// localhost:8080/files/download/id
            return new ImageFileDTO(imFile.getName(),
                    imageUri,
                    imFile.getType(),
                    imFile.getLength());
        }).collect(Collectors.toList());

        return imageFileDTOS;

    }

    public void removeById(String id) {
        ImageFile imageFile =  getImageById(id);
        imageFileRepository.delete(imageFile);
    }

    public ImageFile findImageById(String imageId) {
        return imageFileRepository.findImageById(imageId).orElseThrow(()->
                new ResourceNotFoundException(
                        String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, imageId)));
    }
}
