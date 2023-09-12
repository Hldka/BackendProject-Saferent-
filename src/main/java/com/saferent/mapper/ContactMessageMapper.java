package com.saferent.mapper;

import com.saferent.domain.*;
import com.saferent.dto.*;
import com.saferent.dto.request.*;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = "spring") // I can inject and use any class
public interface ContactMessageMapper {

    // !!! ContactMessage ---> ContactMessageDTO
    ContactMessageDTO contactMessageToDTO(ContactMessage contactMessage);

    // !!! ContactMessageRequest ---> ContactMessage
    @Mapping(target="id", ignore = true) // We state that no mappling is done because DTO is not id either
    ContactMessage contactMessageRequestToContactMessage(ContactMessageRequest contactMessageRequest);

    // !!! List<ContactMessage> ---> List<ContactMessageDTO>
    List<ContactMessageDTO> map(List<ContactMessage> contactMessageList); // getAllContactMessage()


}
