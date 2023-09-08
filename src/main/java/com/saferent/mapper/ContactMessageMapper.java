package com.saferent.mapper;

import com.saferent.domain.*;
import com.saferent.dto.*;
import com.saferent.dto.request.*;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = "spring") // herhangi bir sınıf enjekte edip kullanabilirim
public interface ContactMessageMapper {

    // !!! ContactMessage ---> ContactMessageDTO
    ContactMessageDTO contactMessageToDTO(ContactMessage contactMessage);

    // !!! ContactMessageRequest ---> ContactMessage
    @Mapping(target="id", ignore = true) // DTO da id olmadığı için mappleme yapılmamasını belirtiyoruz
    ContactMessage contactMessageRequestToContactMessage(ContactMessageRequest contactMessageRequest);

    // !!! List<ContactMessage> ---> List<ContactMessageDTO>
    List<ContactMessageDTO> map(List<ContactMessage> contactMessageList); // getAllContactMessage()


}
