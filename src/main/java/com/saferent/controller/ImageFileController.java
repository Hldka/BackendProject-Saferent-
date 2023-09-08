package com.saferent.controller;

import com.saferent.domain.*;
import com.saferent.dto.*;
import com.saferent.dto.response.*;
import com.saferent.service.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.util.*;

@RestController
@RequestMapping("/files")
public class ImageFileController {

    private final ImageFileService imageFileService;

    public ImageFileController(ImageFileService imageFileService) {
        this.imageFileService = imageFileService;
    }

    //!!! ************ Upload **********
        // imageId : 31361f23-8148-40ce-93ca-23760906aabc
    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImageSavedResponse> uploadFile(
            @RequestParam("file") MultipartFile file){

         String imageId = imageFileService.saveImage(file);

         ImageSavedResponse response =
                 new ImageSavedResponse(
                         imageId,ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE,true);
         return ResponseEntity.ok(response);

    }

    // !!! Download
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id){
        ImageFile imageFile = imageFileService.getImageById(id);

        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + imageFile.getName()).
                body(imageFile.getImageData().getData());
    }

    // !!!  ******** Image Display *********
    @GetMapping("/display/{id}")
    public ResponseEntity<byte[]> displayFile(@PathVariable String id) {
        ImageFile imageFile = imageFileService.getImageById(id);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(
                imageFile.getImageData().getData(),
                header,
                HttpStatus.OK);
    }

    //!!! ******** GetAllImages ************
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ImageFileDTO>> getAllImages(){
        List<ImageFileDTO> allImageDTO = imageFileService.getAllImages();

        return ResponseEntity.ok(allImageDTO);
    }

    //!!! ******** Delete Image ************
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SfResponse> deleteImageFile(@PathVariable String id) {
        imageFileService.removeById(id);

        SfResponse response = new SfResponse(
                ResponseMessage.IMAGE_DELETED_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(response);
    }



}
