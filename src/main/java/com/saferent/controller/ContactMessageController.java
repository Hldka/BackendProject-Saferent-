package com.saferent.controller;

import com.saferent.domain.*;
import com.saferent.dto.*;
import com.saferent.dto.request.*;
import com.saferent.dto.response.*;
import com.saferent.mapper.*;
import com.saferent.service.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@RestController
@RequestMapping("/contactmessage")  // http://localhost:8080/contactmessage/
public class ContactMessageController {

    //@Autowired // field injection yapmamak için commente alındı
    private final ContactMessageService contactMessageService;

    private final ContactMessageMapper contactMessageMapper;

    //@Autowired
    public ContactMessageController(ContactMessageService contactMessageService, ContactMessageMapper contactMessageMapper) {
        this.contactMessageService = contactMessageService;
        this.contactMessageMapper = contactMessageMapper;
    }

    //!!! create ContactMessage
    @PostMapping("/visitors")
    public ResponseEntity<SfResponse> createMessage(@Valid @RequestBody ContactMessageRequest contactMessageRequest){
        // bana gelen DTO yu POJO ya çevirmek için mapStruct yapısını kullanacağım
       ContactMessage contactMessage =
               contactMessageMapper.contactMessageRequestToContactMessage(contactMessageRequest);
       contactMessageService.saveMessage(contactMessage);

       SfResponse response = new SfResponse("ContactMessage successfully created", true);

       return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    //!!! getaLL ContactMessages
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactMessageDTO>> getAllContactMessage() {
        List<ContactMessage> contactMessageList =  contactMessageService.getAll();
        //mapStruct ( POJOs -> DTOs )
       List<ContactMessageDTO> contactMessageDTOList = contactMessageMapper.map(contactMessageList);

       return ResponseEntity.ok(contactMessageDTOList); //   return new ResponseEntity<>(contactMessageDTOList, HttpStatus.OK);

    }

    // !!! // pageable  (  Server Side Paging - Client Side Paging )
    @GetMapping("/pages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ContactMessageDTO>> getAllContactMessageWithPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop, // neye göre sıralanacagını belirtiyoruz
            @RequestParam(value="direction",
                          required = false,
                          defaultValue = "DESC")Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<ContactMessage> contactMessagePage = contactMessageService.getAll(pageable);
        Page<ContactMessageDTO> pageDTO = getPageDTO(contactMessagePage);

        return ResponseEntity.ok(pageDTO);
    }

   // !!! spesifik olarak bir ContactMessage PathVariable ile alalım
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactMessageDTO> getMessageWithPath(@PathVariable("id") Long id) {

        ContactMessage contactMessage = contactMessageService.getContactMessage(id);

        ContactMessageDTO contactMessageDTO = contactMessageMapper.contactMessageToDTO(contactMessage);

        return ResponseEntity.ok(contactMessageDTO);

    }

    // !!! spesifik olarak bir ContactMessage RequestParam ile alalım
    @GetMapping("/request")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactMessageDTO> getMessageWithRequestParam(
                                        @RequestParam("id") Long id) {
        ContactMessage contactMessage = contactMessageService.getContactMessage(id);
        ContactMessageDTO contactMessageDTO = contactMessageMapper.contactMessageToDTO(contactMessage);

        return ResponseEntity.ok(contactMessageDTO);

    }

    //!!! Delete işlemi
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SfResponse> deleteContactMessage(@PathVariable Long id){
        contactMessageService.deleteContactMessage(id);

        SfResponse sfResponse = new SfResponse(ResponseMessage.CONTACTMESSAGE_DELETE_RESPONSE,true);

        return ResponseEntity.ok(sfResponse);
    }

    //!!! update
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SfResponse> updateContactMessage( @PathVariable Long id,
                 @Valid @RequestBody ContactMessageRequest contactMessageRequest) {
        ContactMessage contactMessage =
          contactMessageMapper.contactMessageRequestToContactMessage(contactMessageRequest);
        contactMessageService.updateContactMessage(id,contactMessage);

        SfResponse sfResponse =
                new SfResponse(ResponseMessage.CONTACTMESSAGE_UPDATE_RESPONSE,true);

        return ResponseEntity.ok(sfResponse);
    }






    //!!! getPageDTO
    private Page<ContactMessageDTO> getPageDTO(Page<ContactMessage> contactMessagePage){

        return contactMessagePage.map(  // map methodu Page yapısından geliyor
                contactMessage -> contactMessageMapper.contactMessageToDTO(contactMessage));

    }



}
